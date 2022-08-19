package org.vshmaliukh.services.file_service.sql_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

@Slf4j
public class MySqlHandler extends AbstractSqlItemHandler {

    private static final String MYSQL_USER_NAME = "test";
    private static final String MYSQL_PASSWORD = "test";
    private static final String MYSQL_DB_URL = "jdbc:mysql://127.0.0.1:3307/my_test";

    public Connection getConnectionToDB() {
        try {
            return DriverManager.getConnection(MYSQL_DB_URL, MYSQL_USER_NAME, MYSQL_PASSWORD);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
        return null;
    }

    public void logSqlHandler(Exception e) {
        log.error("[MySql_handler] got err. Exception: ", e);
    }

    public MySqlHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    @Override
    protected void setUpSettings() {
        createUser();
        generateTablesIfNotExists();
    }

    @Override
    public void createNewTable(String sql) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    @Override
    public String generateFullFileName() {
        return "";
    }

    @Override
    public Path generatePathForFileHandler() { // todo destruct
        String sqlLiteHandlerFolderStr = "sqlLite_handler";
        Path path = Paths.get(String.valueOf(generatePathForUser()), sqlLiteHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    @Override
    public void saveItemToDB(Item item) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String sqlInsertStr = handlerByClass.insertItemMySqlStr();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertStr);
                handlerByClass.insertItemValuesToSqlDB(preparedStatement, item, user.getId());
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    @Override
    public void insertUser(String userName) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            String sql = " INSERT IGNORE INTO " + USER_TABLE_TITLE + " ( " + USER_NAME_SQL_PARAMETER + " ) VALUES(?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userName);
                preparedStatement.executeUpdate();
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    @Override
    public void createUser() {
        String sql = " CREATE TABLE IF NOT EXISTS " + USER_TABLE_TITLE + " (\n" +
                USER_ID_SQL_PARAMETER + " INT AUTO_INCREMENT PRIMARY KEY , \n" +
                USER_NAME_SQL_PARAMETER + " VARCHAR(255) NOT NULL , \n" +
                " UNIQUE ( \n" +
                USER_NAME_SQL_PARAMETER + " )\n" +
                ");";
        createNewTable(sql);
        insertUser(user.getName());
        readUserId(user);
    }

    @Override
    public void readUserId(UserContainer user) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            String sql = "" +
                    " SELECT " + USER_ID_SQL_PARAMETER +
                    " FROM " + USER_TABLE_TITLE +
                    " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getName());

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
                }
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    @Override
    public void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.createTableMySqlStr();
            createNewTable(tableStr);
        }
    }
}

