package com.vshmaliukh.springbootshelfcore.shelf.sqlite;

import com.vshmaliukh.springbootshelfcore.shelf.SqlSpringBootHandler;
import com.vshmaliukh.springbootshelfcore.shelf.sqlite.services.SqliteItemServiceImp;
import com.vshmaliukh.springbootshelfcore.shelf.sqlite.services.SqliteUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class SqliteSpringBootHandler extends SqlSpringBootHandler {

    public SqliteSpringBootHandler(SqliteItemServiceImp sqliteItemServiceImp, SqliteUserServiceImp sqliteUserServiceImp) {
        this.itemServiceImp = sqliteItemServiceImp;
        this.userServiceImp = sqliteUserServiceImp;
    }

}
