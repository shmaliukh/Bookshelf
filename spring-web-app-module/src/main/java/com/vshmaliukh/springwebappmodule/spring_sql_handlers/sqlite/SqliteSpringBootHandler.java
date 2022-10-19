package com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.SqlSpringBootHandler;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.services.SqliteItemServiceImp;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.services.SqliteUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class SqliteSpringBootHandler extends SqlSpringBootHandler {

    public SqliteSpringBootHandler(SqliteItemServiceImp sqliteItemServiceImp, SqliteUserServiceImp sqliteUserServiceImp) {
        this.itemServiceImp = sqliteItemServiceImp;
        this.userServiceImp = sqliteUserServiceImp;
    }

}
