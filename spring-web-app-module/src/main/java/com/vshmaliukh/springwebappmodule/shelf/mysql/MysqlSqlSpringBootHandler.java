package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.SqlSpringBootHandler;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlSqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlSqlUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class MysqlSqlSpringBootHandler extends SqlSpringBootHandler {

    public MysqlSqlSpringBootHandler(MysqlSqlItemServiceImp mysqlItemServiceImp, MysqlSqlUserServiceImp mysqlUserServiceImp) {
        this.itemServiceImp = mysqlItemServiceImp;
        this.userServiceImp = mysqlUserServiceImp;
    }

}
