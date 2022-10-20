package com.vshmaliukh.spring_shelf_core.shelf.mysql;

import com.vshmaliukh.spring_shelf_core.shelf.AbstractSqlSpringBootHandler;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.services.MysqlUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class MysqlSpringBootHandler extends AbstractSqlSpringBootHandler {

    public MysqlSpringBootHandler(MysqlItemServiceImp mysqlItemServiceImp,
                                  MysqlUserServiceImp mysqlUserServiceImp) {
        this.itemServiceImp = mysqlItemServiceImp;
        this.userServiceImp = mysqlUserServiceImp;
    }
}
