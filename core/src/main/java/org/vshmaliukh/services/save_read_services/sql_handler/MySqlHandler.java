package org.vshmaliukh.services.save_read_services.sql_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.sql.*;
import java.util.List;

@Slf4j
public class MySqlHandler extends AbstractSqlHandler {

    public static final String MYSQL_USER_NAME_ENV = "MYSQL_USER_NAME";
    public static final String MYSQL_PASSWORD_ENV = "MYSQL_PASSWORD";
    public static final String MYSQL_PORT_ENV = "MYSQL_PORT";
    public static final String MYSQL_DB_NAME_ENV = "MYSQL_DB_NAME";

    private static final String MYSQL_USER_NAME;
    private static final String MYSQL_PASSWORD;
    private static final String MYSQL_DB_URL;

    static {
        String mysqlUserName = System.getenv(MYSQL_USER_NAME_ENV);
        if (mysqlUserName == null || mysqlUserName.length() == 0) {
            mysqlUserName = "test";
        }
        MYSQL_USER_NAME = mysqlUserName;

        String mysqlPassword = System.getenv(MYSQL_PASSWORD_ENV);
        if (mysqlPassword == null || mysqlPassword.length() == 0) {
            mysqlPassword = "test";
        }
        MYSQL_PASSWORD = mysqlPassword;

        String mysqlPort = System.getenv(MYSQL_PORT_ENV);
        if (mysqlPort == null || mysqlPort.length() == 0) {
            mysqlPort = "localhost:3307";
        }
        String mysqlDbName = System.getenv(MYSQL_DB_NAME_ENV);
        if (mysqlDbName == null || mysqlDbName.length() == 0) {
            mysqlDbName = "my_test";
        }
        MYSQL_DB_URL = "jdbc:mysql://localhost:3307/my_test";
//                + mysqlPort + "/" + mysqlDbName;
    }

    protected Connection connectionToMySqlDB = null;

    public MySqlHandler(String homeDir, String userName) {
        super(userName);
    }

    public Connection getConnectionToDB() {
        if (connectionToMySqlDB == null) {
            try {
                connectionToMySqlDB = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_USER_NAME, MYSQL_PASSWORD);
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
                getConnectionToDB();
            }
        }
        return connectionToMySqlDB;
    }

    public void logSqlHandler(Exception e) {
        log.error("[MySql_handler] got err. Exception: ", e);
    }

    @Override
    public void setUpSettings() {
        createUser();
        generateTablesIfNotExists();
    }

    @Override
    public void createNewTable(String sql) {
        try (Statement stmt = getConnectionToDB().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    @Override
    public void saveItemToDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String sqlInsertStr = handlerByClass.insertItemMySqlStr();
        try {
            PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sqlInsertStr);
            handlerByClass.insertItemValuesToSqlDB(preparedStatement, item, userContainer.getId());
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void insertUser(String userName) {
        if (!isUserExist(userName)) {
            String sql = " INSERT INTO " + USER_TABLE_TITLE + " ( " + USER_NAME_SQL_PARAMETER + " ) " + " VALUES ( ? ) ";
            try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
                preparedStatement.setString(1, userName);
                preparedStatement.executeUpdate();
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    private boolean isUserExist(String userName) {
        String sql = " SELECT COUNT(" + USER_NAME_SQL_PARAMETER + ")" +
                " FROM " + USER_TABLE_TITLE +
                " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
            preparedStatement.setString(1, userName);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int anInt = rs.getInt(1);
                if (anInt == 0) {
                    return false;
                }
            }
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
        return true;
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
        insertUser(userContainer.getName());
        readUserId(userContainer);
    }

    @Override
    public void readUserId(UserContainer user) {
        String sql = "" +
                " SELECT " + USER_ID_SQL_PARAMETER +
                " FROM " + USER_TABLE_TITLE +
                " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
            }
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
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

