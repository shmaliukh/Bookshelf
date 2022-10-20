package com.vshmaliukh.spring_shelf_core.shelf.mysql.services;

import com.vshmaliukh.spring_shelf_core.shelf.SqlItemServiceImp;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.MysqlItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class MysqlItemServiceImp extends SqlItemServiceImp {

    public MysqlItemServiceImp(MysqlItemEntityRepositoryActionsProvider itemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = itemEntityRepositoryProvider;
    }

}
