package com.vshmaliukh.springbootshelfcore.shelf.mysql;

import com.vshmaliukh.springbootshelfcore.shelf.SqlSpringBootHandler;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.services.MysqlUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class MysqlSpringBootHandler extends SqlSpringBootHandler {

    public MysqlSpringBootHandler(MysqlItemServiceImp mysqlItemServiceImp,
                                  MysqlUserServiceImp mysqlUserServiceImp) {
        this.itemServiceImp = mysqlItemServiceImp;
        this.userServiceImp = mysqlUserServiceImp;
    }
}
