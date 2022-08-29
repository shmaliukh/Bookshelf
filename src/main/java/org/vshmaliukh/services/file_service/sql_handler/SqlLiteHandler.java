package org.vshmaliukh.services.file_service.sql_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

@Slf4j
public class SqlLiteHandler extends AbstractSqlItemHandler {

    public static final String SQL_FILE_TYPE = ".db";
    public static final String SQLLITE_FILE_NAME = "shelf_sqllite_db" + SQL_FILE_TYPE;
    private static final String SQLLITE_FILE_URL = "jdbc:sqlite:" + Paths.get(System.getProperty("user.home"), PROGRAM_DIR_NAME, SQLLITE_FILE_NAME); // todo

    private Connection connectionToSqlLiteDB = null;

    public SqlLiteHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    @Override
    protected void setUpSettings() {
        createNewDatabaseIfNotExists();
        connectToDB();
        createUser();
        generateTablesIfNotExists();
    }

    public Connection getConnectionToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (connectionToSqlLiteDB == null) {
            try {
                connectionToSqlLiteDB = DriverManager.getConnection(SQLLITE_FILE_URL);
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
                getConnectionToDB();
            }
        }
        return connectionToSqlLiteDB;
    }

    void createNewDatabaseIfNotExists() {
        try {
            DatabaseMetaData meta = getConnectionToDB().getMetaData();
            log.info("The driver name is " + meta.getDriverName());
            log.info("A new database '" + SQLLITE_FILE_URL + "' has been created.");

        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    void connectToDB() {
        try {
            connectionToSqlLiteDB = DriverManager.getConnection(SQLLITE_FILE_URL);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void createNewTable(String sql) {
        try (Statement stmt = getConnectionToDB().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void logSqlHandler(Exception e) {
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

    public void saveItemToDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String sqlInsertStr = handlerByClass.insertItemSqlLiteStr();
        try {
            PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sqlInsertStr);
            handlerByClass.insertItemValuesToSqlDB(preparedStatement, item, user.getId());
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void insertUser(String userName) {
        String sql = "INSERT OR IGNORE INTO " + USER_TABLE_TITLE + " ( " + USER_NAME_SQL_PARAMETER + " ) VALUES(?)";
        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void createUser() {
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

    public void readUserId(UserContainer user) {
        String sql = "" +
                " SELECT " + USER_ID_SQL_PARAMETER +
                " FROM " + USER_TABLE_TITLE +
                " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());

            ResultSet rs = preparedStatement.executeQuery();
            user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    public void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.createTableSqlLiteStr();
            createNewTable(tableStr);
        }
    }
}

