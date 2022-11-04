package org.vshmaliukh.services.save_read_services.sql_handler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.save_read_services.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import static org.vshmaliukh.BaseAppConfig.HOME_PROPERTY;

@Slf4j
@NoArgsConstructor
public class SqliteHandler extends AbstractSqlHandler implements SaveReadUserFilesHandler {

    public static final String SQL_FILE_TYPE = ".db";
    public static final String SQLITE_FILE_NAME = "shelf_sqlite_db" + SQL_FILE_TYPE;
    private static final String SQLITE_FILE_URL = "jdbc:sqlite:" + Paths.get(System.getProperty("user.home"), PROGRAM_DIR_NAME, SQLITE_FILE_NAME);

    private Connection connectionToSqlLiteDB = null;

    public SqliteHandler(String userName) {
        super(userName);
    }

    @Override
    public void setUpSettings() {
        createNewDatabaseIfNotExists();
        connectToDB();
        createUser();
        generateTablesIfNotExists();
    }

    @Override
    public Connection getConnectionToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException cnfe) {
            logSqlHandler(cnfe);
        }
        if (connectionToSqlLiteDB == null) {
            try {
                connectionToSqlLiteDB = DriverManager.getConnection(SQLITE_FILE_URL);
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
            log.info("A new database '" + SQLITE_FILE_URL + "' has been created.");

        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    void connectToDB() {
        try {
            connectionToSqlLiteDB = DriverManager.getConnection(SQLITE_FILE_URL);
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    @Override
    public void logSqlHandler(Exception e) {
        log.error("[SqlLite_handler] got err. Exception: ", e);
    }

    @Override
    public String generateFullFileName() {
        return SQLITE_FILE_NAME;
    }

    @Override
    public Path generatePathForFileHandler() {
        String sqlLiteHandlerFolderStr = "sqlLite_handler";
        Path path = Paths.get(String.valueOf(generatePathForUser(HOME_PROPERTY, this.userName)), sqlLiteHandlerFolderStr);
        createDirectoryIfNotExists(path, this.userName);
        return path;
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

    @Override
    public void createUser() {
        String sql = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_TITLE + " \n" +
                "(\n" +
                USER_ID_SQL_PARAMETER + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                USER_NAME_SQL_PARAMETER + " TEXT NOT NULL, \n" +
                "UNIQUE (" + USER_NAME_SQL_PARAMETER + ") ON CONFLICT IGNORE \n" +
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
            user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    @Override
    public void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.createTableSqlLiteStr();
            createNewTable(tableStr);
        }
    }
}

