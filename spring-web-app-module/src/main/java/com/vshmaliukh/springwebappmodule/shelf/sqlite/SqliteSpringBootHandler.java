package com.vshmaliukh.springwebappmodule.shelf.sqlite;

import com.vshmaliukh.springwebappmodule.shelf.SqlSpringBootHandler;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.services.SqliteItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.services.SqliteUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class SqliteSpringBootHandler extends SqlSpringBootHandler {

    public SqliteSpringBootHandler(SqliteItemServiceImp sqliteItemServiceImp, SqliteUserServiceImp sqliteUserServiceImp) {
        this.itemServiceImp = sqliteItemServiceImp;
        this.userServiceImp = sqliteUserServiceImp;
    }

}
