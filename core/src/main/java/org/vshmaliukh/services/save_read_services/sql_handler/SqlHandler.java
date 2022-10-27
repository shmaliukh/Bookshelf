package org.vshmaliukh.services.save_read_services.sql_handler;

import org.vshmaliukh.shelf.shelf_handler.User;

import java.sql.Connection;

public interface SqlHandler extends BaseSqlHandler {

    String USER_ID_SQL_PARAMETER = "id";
    String USER_NAME_SQL_PARAMETER = "user_name";
    String USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES = "user_id";
    String USER_TABLE_TITLE = User.class.getSimpleName() + "s";

    default Connection getConnectionToDB(){return null;}

    default void createNewTable(String sql) {}

    default void generateTablesIfNotExists(){}

    default void logSqlHandler(Exception e){}

    default void createUser(){}

}
