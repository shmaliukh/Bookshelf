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

    public static final String USER_NAME = "USER_NAME"; // todo
    public static final String USER_ID = "USER_ID";
    public static final String USER_TABLE_TITLE = User.class.getSimpleName() + "s";

    public static final String SQL_FILE_TYPE = ".db";
    private final String dburl;// TODO
    final User user;

    Connection conn = null;

    public SqlLiteHandler(String homeDir, User user) {
        super(homeDir, user.getName());
        this.user = user;

        dburl = "jdbc:sqlite:" + Paths.get(programDirectoryStr, generateFullFileName());
        connectToDB();
        //createNewDatabase();
        registrateUser();
        generateTablesIfNotExists(); // TODO
    }

    void createNewDatabase() { // TODO should start once
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
        return "sqllite_db" + SQL_FILE_TYPE;
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
        for (Item item : listToSave) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String sqlInsertStr = handlerByClass.insertItemSqlStr();
            try {
                PreparedStatement pstmt = conn.prepareStatement(sqlInsertStr);
                handlerByClass.insertItemValues(pstmt, item, user.getId());
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    public void insertUser(String userName) {
        String sql = "INSERT INTO " + USER_TABLE_TITLE + " ( " + USER_NAME + " ) VALUES(?)";
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
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                USER_NAME + " TEXT NOT NULL, \n" +
                "UNIQUE (" + USER_NAME + ") ON CONFLICT IGNORE \n" +
                ");";
        createNewTable(sql);
        insertUser(user.getName());
        readUserId(user);
    }

    private void readUserId(User user) {
        String sql = "" +
                " SELECT " + USER_ID +
                " FROM " + USER_TABLE_TITLE +
                " WHERE " + USER_NAME + " = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());

            ResultSet rs = pstmt.executeQuery();
            user.setId(rs.getInt(USER_ID));
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
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String sqlStr = handlerByClass.selectItemSqlStr(user.getId());
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sqlStr);
                while (rs.next()) {
                    Item item = handlerByClass.readItemFromSql(rs);
                    itemList.add(item);
                }
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
        return itemList;
    }

    public static void main(String[] args) {
        Random random = new Random();
        SqlLiteHandler sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), new User("test_sql_3"));

        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> uniqueTypeName : ItemHandlerProvider.uniqueTypeNames) {
            itemList.add(ItemHandlerProvider.getHandlerByClass(uniqueTypeName).getRandomItem(random));
            //    itemList.add(ItemHandlerProvider.getHandlerByClass(uniqueTypeName).getRandomItem(random));
        }
        sqlLiteHandler.saveItemList(itemList);
        System.out.println(sqlLiteHandler.readItemList());

        //System.out.println(itemList);
        //System.out.println();
        //System.out.println(sqlLiteHandler.readItemList());
        //new PlainTextTableHandler(new PrintWriter(System.out), ConvertorToStringForItems.getTable(itemList), true).print();
    }
}

