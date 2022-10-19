package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlUserServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;
import org.springframework.stereotype.Service;

@Service
public class MysqlUserServiceImp extends SqlUserServiceImp {

    public MysqlUserServiceImp(MysqlUserRepository mysqlUserRepository) {
        this.userRepository = mysqlUserRepository;
    }

}
