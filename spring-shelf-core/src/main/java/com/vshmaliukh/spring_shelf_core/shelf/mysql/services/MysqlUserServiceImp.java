package com.vshmaliukh.spring_shelf_core.shelf.mysql.services;

import com.vshmaliukh.spring_shelf_core.shelf.SqlUserServiceImp;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories.MysqlUserRepository;
import org.springframework.stereotype.Service;

@Service
public class MysqlUserServiceImp extends SqlUserServiceImp {

    public MysqlUserServiceImp(MysqlUserRepository mysqlUserRepository) {
        this.userRepository = mysqlUserRepository;
    }

}
