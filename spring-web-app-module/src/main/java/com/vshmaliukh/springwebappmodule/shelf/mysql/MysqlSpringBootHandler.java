package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.SqlSpringBootHandler;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class MysqlSpringBootHandler extends SqlSpringBootHandler {

    public MysqlSpringBootHandler(MysqlItemServiceImp mysqlItemServiceImp, MysqlUserServiceImp mysqlUserServiceImp) {
        this.itemServiceImp = mysqlItemServiceImp;
        this.userServiceImp = mysqlUserServiceImp;
    }

}
