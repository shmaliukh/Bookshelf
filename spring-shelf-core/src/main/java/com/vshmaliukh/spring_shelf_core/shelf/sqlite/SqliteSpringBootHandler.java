package com.vshmaliukh.spring_shelf_core.shelf.sqlite;

import com.vshmaliukh.spring_shelf_core.shelf.AbstractSqlSpringBootHandler;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.services.SqliteItemServiceImp;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.services.SqliteUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class SqliteSpringBootHandler extends AbstractSqlSpringBootHandler {

    public SqliteSpringBootHandler(SqliteItemServiceImp sqliteItemServiceImp, SqliteUserServiceImp sqliteUserServiceImp) {
        this.itemServiceImp = sqliteItemServiceImp;
        this.userServiceImp = sqliteUserServiceImp;
    }

}
