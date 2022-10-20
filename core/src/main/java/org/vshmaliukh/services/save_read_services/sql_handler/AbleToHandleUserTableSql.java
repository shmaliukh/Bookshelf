package org.vshmaliukh.services.save_read_services.sql_handler;

import org.vshmaliukh.shelf.shelf_handler.User;

public interface AbleToHandleUserTableSql {

    String USER_ID_SQL_PARAMETER = "id";
    String USER_NAME_SQL_PARAMETER = "user_name";
    String USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES = "user_id";
    String USER_TABLE_TITLE = User.class.getSimpleName() + "s";

    void insertUser(String userName);

    void createUser();

    void readUserId(UserContainer user);

}
