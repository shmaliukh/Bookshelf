package org.vshmaliukh.shelf.shelf_handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class User {

    public static final String USER_NAME_SQL_PARAMETER = "user_name";
    public static final String USER_ID_SQL_PARAMETER = "user_id";
    public static final String USER_TABLE_TITLE = User.class.getSimpleName() + "s";

    protected Integer id = null;

    private final String name;

    public User(String name) {
        this.name = name;
    }
}
