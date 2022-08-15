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

@Slf4j
public class SqlLiteHandler extends SaveReadUserFilesHandler {

    public static final String USER_NAME = "user_name"; // todo
    public static final String USER_ID = "user_id";
    public static final String USERS = "users";

    public static final String SQL_FILE_TYPE = ".db";
    private final String dburl;// TODO

    Connection conn = null;

    public SqlLiteHandler(String homeDir, User user) {
        super(homeDir, user.getName());

        dburl = "jdbc:sqlite:" + Paths.get(generatePathForFileHandler().toString(), generateFullFileName());
        connectToDB();
        createNewDatabase();
        registrateUser(user);
        //generateTablesIfNotExists(); // TODO
    }

    void createNewDatabase() { // TODO should all methods return boolean (?)
        try {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.info("The driver name is " + meta.getDriverName());
                log.info("A new database '" + dburl + "' has been created.");
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    private void connectToDB() {
        try {
            conn = DriverManager.getConnection(dburl);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public static void logSqlHandler(Exception e) {
        log.error("[SqlLite_handler] got err. Exception: ", e);
    }

    @Override
    public String generateFullFileName() {
        return userName + "_sqllite_db" + SQL_FILE_TYPE;
    }

    @Override
    public Path generatePathForFileHandler() {
        String sqlLiteHandlerFolderStr = "sqlLite_handler";
        Path path = Paths.get(String.valueOf(generatePathForUser()), sqlLiteHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    public String getDburl() {
        return dburl;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        for (Item item : listToSave) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String sqlInsertStr = handlerByClass.generateSqlInsertStr(item);
            List<String> parameterList = handlerByClass.parameterList();
            Map<String, String> convertItemToListOfString = handlerByClass.convertItemToListOfString(item);
            SqlLiteUtils.insert(dburl, sqlInsertStr, parameterList, convertItemToListOfString);
        }
    }

    public void createNewTable(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void insertUser(String userName) {
        String sql = "INSERT INTO " + USERS + " ( " + USER_NAME + " ) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    private void registrateUser(User user) {
        String sql = "CREATE TABLE IF NOT EXISTS " + USERS + " \n" +
                "(\n" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                USER_NAME + " TEXT NOT NULL, \n" +
                "UNIQUE (" + USER_NAME + ") ON CONFLICT IGNORE \n" +
                ");";
        createNewTable(sql);
        insertUser(user.getName());
        getUserId(user);
    }

    private void getUserId(User user) {
        String sql = "SELECT " + USER_ID + " FROM " + USERS;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            user.setId(rs.getInt(USER_ID));
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    private void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.generateSqlTableStr(classType);
            createNewTable(tableStr);
        }
    }

    @Override
    public List<Item> readItemList() {
        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String sqlSelectAll = handlerByClass.generateSqlSelectAllParametersByClass(classType);
            List<Map<String, String>> itemMaps = SqlLiteUtils.selectAllByClass(dburl, sqlSelectAll, handlerByClass.parameterList());
            for (Map<String, String> itemMap : itemMaps) {
                Item item = handlerByClass.generateItemByParameterValueMap(itemMap);
                itemList.add(item);
            }
        }
        return itemList;
    }

    public static void main(String[] args) {
        Random random = new Random();
        SqlLiteHandler sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), new User("test_sql"));

        List<Item> itemList = new ArrayList<>();
        //for (Class<? extends Item> uniqueTypeName : ItemHandlerProvider.uniqueTypeNames) {
        //    itemList.add(ItemHandlerProvider.getHandlerByClass(uniqueTypeName).getRandomItem(random));
        //    itemList.add(ItemHandlerProvider.getHandlerByClass(uniqueTypeName).getRandomItem(random));
        //}

        //System.out.println(itemList);
        //System.out.println();
        //System.out.println(sqlLiteHandler.readItemList());
        //new PlainTextTableHandler(new PrintWriter(System.out), ConvertorToStringForItems.getTable(itemList), true).print();
    }
}

