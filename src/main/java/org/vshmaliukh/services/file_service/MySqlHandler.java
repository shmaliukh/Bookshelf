package org.vshmaliukh.services.file_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.shelf.shelf_handler.User.*;

@Slf4j
public class MySqlHandler extends SaveReadUserFilesHandler {

    public static final String USER_ID_SQL_PARAMETER = "id";

    private static final String MYSQL_USER_NAME = "test";
    private static final String MYSQL_PASSWORD = "test";
    private static final String MYSQL_DB_URL = "jdbc:mysql://127.0.0.1:3307/my_test";

    private static Connection conn;

    final User user; // TODO is necessary to keep reference to the user

    static {
        createNewDatabase();
        connectToDB();
    }

    public MySqlHandler(String homeDir, String userName) {
        super(homeDir, userName);
        this.user = new User(userName);
        createUser();
        generateTablesIfNotExists();
    }

    static void createNewDatabase() {
        try {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.info("The driver name is " + meta.getDriverName());
                log.info("A new database '" + MYSQL_DB_URL + "' has been created.");
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    static void connectToDB() {
        try {
            conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_USER_NAME, MYSQL_PASSWORD);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void createNewTable(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public static void logSqlHandler(Exception e) {
        log.error("[MySql_handler] got err. Exception: ", e);
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

    public void saveItemToDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String sqlInsertStr = handlerByClass.insertItemMySqlStr();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertStr);
            handlerByClass.insertItemValuesToSqlDB(preparedStatement, item, user.getId());
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void insertUser(String userName) {
        String sql = " INSERT IGNORE INTO " + USER_TABLE_TITLE + " ( " + USER_NAME_SQL_PARAMETER + " ) VALUES(?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    private void createUser() {
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

    private void readUserId(User user) {
        String sql = "" +
                " SELECT " + USER_ID_SQL_PARAMETER +
                " FROM " + USER_TABLE_TITLE +
                " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
            }
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    private void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.createTableMySqlStr();
            createNewTable(tableStr);
        }
    }

    @Override
    public List<Item> readItemList() {
        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            List<? extends Item> itemByClassList = readItemsByClass(classType);
            itemList.addAll(itemByClassList);
        }
        return itemList;
    }

    public void deleteItemFromDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String deleteItemFromDBStr = handlerByClass.deleteItemSqlStr();
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteItemFromDBStr)) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.execute();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void changeItemBorrowedStateInDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String changeItemBorrowedStateInDB = handlerByClass.changeItemBorrowedStateSqlStr();
        try (PreparedStatement preparedStatement = conn.prepareStatement(changeItemBorrowedStateInDB)) {
            preparedStatement.setString(1, String.valueOf(!item.isBorrowed()));
            preparedStatement.setInt(2, item.getId());
            preparedStatement.execute();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        ItemHandler<T> handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
        String sqlStr = handlerByClass.selectItemSqlStr();
        List<T> itemByClassList = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStr)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = handlerByClass.readItemFromSqlDB(resultSet);
                itemByClassList.add(item);
            }
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
        return itemByClassList;
    }
}

