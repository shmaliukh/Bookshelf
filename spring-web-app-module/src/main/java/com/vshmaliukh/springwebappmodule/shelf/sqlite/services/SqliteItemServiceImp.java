package com.vshmaliukh.springwebappmodule.shelf.sqlite.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.SqliteItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class SqliteItemServiceImp extends SqlItemServiceImp {

    public SqliteItemServiceImp(SqliteItemEntityRepositoryActionsProvider sqliteItemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = sqliteItemEntityRepositoryProvider;
    }

}
