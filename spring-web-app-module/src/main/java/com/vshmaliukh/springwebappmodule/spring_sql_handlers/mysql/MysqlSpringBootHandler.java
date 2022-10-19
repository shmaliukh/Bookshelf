package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.SqlSpringBootHandler;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.services.MysqlUserServiceImp;
import org.springframework.stereotype.Service;

@Service
public class MysqlSpringBootHandler extends SqlSpringBootHandler {

    public MysqlSpringBootHandler(MysqlItemServiceImp mysqlItemServiceImp, MysqlUserServiceImp mysqlUserServiceImp) {
        this.itemServiceImp = mysqlItemServiceImp;
        this.userServiceImp = mysqlUserServiceImp;
    }
}
