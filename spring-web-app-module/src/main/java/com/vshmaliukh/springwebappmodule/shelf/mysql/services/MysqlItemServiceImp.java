package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlItemService;
import com.vshmaliukh.springwebappmodule.shelf.mysql.MysqlItemEntityRepositoryProvider;
import org.springframework.stereotype.Service;

@Service
public class MysqlItemServiceImp extends SqlItemService {

    public MysqlItemServiceImp(MysqlItemEntityRepositoryProvider itemEntityRepositoryProvider) {
        this.itemEntityRepositoryProvider = itemEntityRepositoryProvider;
    }

}
