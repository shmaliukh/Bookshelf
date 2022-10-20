package com.vshmaliukh.springbootshelfcore.shelf.mysql.services;

import com.vshmaliukh.springbootshelfcore.shelf.SqlUserServiceImp;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories.MysqlUserRepository;
import org.springframework.stereotype.Service;

@Service
public class MysqlUserServiceImp extends SqlUserServiceImp {

    public MysqlUserServiceImp(MysqlUserRepository mysqlUserRepository) {
        this.userRepository = mysqlUserRepository;
    }

}
