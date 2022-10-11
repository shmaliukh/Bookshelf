package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.SqlSpringBootHandler;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlSqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlSqlUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class MysqlSpringBootHandler extends SqlSpringBootHandler {

    public MysqlSpringBootHandler(MysqlSqlItemServiceImp mysqlItemServiceImp, MysqlSqlUserServiceImp mysqlUserServiceImp) {
        this.itemServiceImp = mysqlItemServiceImp;
        this.userServiceImp = mysqlUserServiceImp;
    }

}
