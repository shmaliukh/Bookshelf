package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.SqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.MysqlItemEntityRepositoryActionsProvider;
import org.springframework.stereotype.Service;

@Service
public class MysqlItemServiceImp extends SqlItemServiceImp {

    public MysqlItemServiceImp(MysqlItemEntityRepositoryActionsProvider itemEntityRepositoryProvider) {
        this.itemEntityRepositoryActionsProvider = itemEntityRepositoryProvider;
    }

}
