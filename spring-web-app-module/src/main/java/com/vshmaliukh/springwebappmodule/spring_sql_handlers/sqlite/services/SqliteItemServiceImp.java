package com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.SqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.SqliteItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class SqliteItemServiceImp extends SqlItemServiceImp {

    public SqliteItemServiceImp(SqliteItemEntityRepositoryActionsProvider sqliteItemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = sqliteItemEntityRepositoryProvider;
    }

}
