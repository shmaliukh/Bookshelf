package org.vshmaliukh.services.file_service.sqllite;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import static org.vshmaliukh.shelf.shelf_handler.User.*;

@Slf4j
public class SqlLiteHandler extends SaveReadUserFilesHandler {

    private static Connection conn;

    public static final String SQL_FILE_TYPE = ".db";
    public static final String SQLLITE_FILE_NAME = "shelf_sqllite_db" + SQL_FILE_TYPE;
    private static final String SqlLiteFileURL = "jdbc:sqlite:" + Paths.get( System.getProperty("user.home"), PROGRAM_DIR_NAME, SQLLITE_FILE_NAME); // todo
    final User user; // TODO

    static {
        createNewDatabase();
        connectToDB();
    }

    public SqlLiteHandler(String homeDir, String userName) {
        super(homeDir, userName);
        this.user = new User(userName);
        registrateUser();
        generateTablesIfNotExists();
    }

    // TODO should start once
    static void createNewDatabase() {
        try {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.info("The driver name is " + meta.getDriverName());
                log.info("A new database '" + SqlLiteFileURL + "' has been created.");
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    static void connectToDB() {
        try {
            conn = DriverManager.getConnection(SqlLiteFileURL);
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
        log.error("[SqlLite_handler] got err. Exception: ", e);
    }

    @Override
    public String generateFullFileName() {
        return SQLLITE_FILE_NAME;
    }

    @Override
    public Path generatePathForFileHandler() {
        String sqlLiteHandlerFolderStr = "sqlLite_handler";
        Path path = Paths.get(String.valueOf(generatePathForUser()), sqlLiteHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    public void saveItemToDB(Item item){
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String sqlInsertStr = handlerByClass.insertItemSqlStr();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlInsertStr);
            handlerByClass.insertItemValues(pstmt, item, user.getId());
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void insertUser(String userName) {
        String sql = "INSERT OR IGNORE INTO " + USER_TABLE_TITLE + " ( " + USER_NAME_SQL_PARAMETER + " ) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    private void registrateUser() {
        String sql = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_TITLE + " \n" +
                "(\n" +
                USER_ID_SQL_PARAMETER + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                USER_NAME_SQL_PARAMETER + " TEXT NOT NULL, \n" +
                "UNIQUE (" + USER_NAME_SQL_PARAMETER + ") ON CONFLICT IGNORE \n" +
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
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());

            ResultSet rs = pstmt.executeQuery();
            user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    private void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.generateSqlTableStr();
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
        String deleteItemFromDBStr = handlerByClass.deleteItemFromDBStr();
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteItemFromDBStr)) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.execute();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void changeItemBorrowedStateInDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String changeItemBorrowedStateInDB = handlerByClass.changeItemBorrowedStateInDBStr();
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
        try (PreparedStatement pstmt = conn.prepareStatement(sqlStr)) {
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                T item = handlerByClass.readItemFromSql(rs);
                itemByClassList.add(item);
            }
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
        return itemByClassList;
    }
}

