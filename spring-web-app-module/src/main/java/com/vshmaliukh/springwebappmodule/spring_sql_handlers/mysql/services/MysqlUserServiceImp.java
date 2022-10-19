package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.SqlUserServiceImp;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.repositories.MysqlUserRepository;
import org.springframework.stereotype.Service;

@Service
public class MysqlUserServiceImp extends SqlUserServiceImp {

    public MysqlUserServiceImp(MysqlUserRepository mysqlUserRepository) {
        this.userRepository = mysqlUserRepository;
    }

}
