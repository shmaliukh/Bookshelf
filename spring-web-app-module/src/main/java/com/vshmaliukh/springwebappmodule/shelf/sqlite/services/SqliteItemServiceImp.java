package com.vshmaliukh.springwebappmodule.shelf.sqlite.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.SqliteItemEntityRepositoryProvider;
import org.springframework.stereotype.Service;

@Service
public class SqliteItemServiceImp extends SqlItemServiceImp {

    public SqliteItemServiceImp(SqliteItemEntityRepositoryProvider sqliteItemEntityRepositoryProvider) {
        this.itemEntityRepositoryProvider = sqliteItemEntityRepositoryProvider;
    }

}
