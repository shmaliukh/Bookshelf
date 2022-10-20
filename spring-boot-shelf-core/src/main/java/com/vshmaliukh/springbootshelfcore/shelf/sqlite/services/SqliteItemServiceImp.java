package com.vshmaliukh.springbootshelfcore.shelf.sqlite.services;

import com.vshmaliukh.springbootshelfcore.shelf.SqlItemServiceImp;
import com.vshmaliukh.springbootshelfcore.shelf.sqlite.SqliteItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class SqliteItemServiceImp extends SqlItemServiceImp {

    public SqliteItemServiceImp(SqliteItemEntityRepositoryActionsProvider sqliteItemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = sqliteItemEntityRepositoryProvider;
    }

}
