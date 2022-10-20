package com.vshmaliukh.spring_shelf_core.shelf.sqlite.services;

import com.vshmaliukh.spring_shelf_core.shelf.SqlItemServiceImp;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.SqliteItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class SqliteItemServiceImp extends SqlItemServiceImp {

    public SqliteItemServiceImp(SqliteItemEntityRepositoryActionsProvider sqliteItemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = sqliteItemEntityRepositoryProvider;
    }

}
