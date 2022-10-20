package com.vshmaliukh.springbootshelfcore.shelf.mysql.services;

import com.vshmaliukh.springbootshelfcore.shelf.SqlItemServiceImp;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.MysqlItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class MysqlItemServiceImp extends SqlItemServiceImp {

    public MysqlItemServiceImp(MysqlItemEntityRepositoryActionsProvider itemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = itemEntityRepositoryProvider;
    }

}
