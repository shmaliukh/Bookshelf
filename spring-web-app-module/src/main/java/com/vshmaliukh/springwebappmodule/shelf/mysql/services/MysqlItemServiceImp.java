package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.MysqlItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class MysqlItemServiceImp extends SqlItemServiceImp {

    public MysqlItemServiceImp(MysqlItemEntityRepositoryActionsProvider itemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = itemEntityRepositoryProvider;
    }

}
