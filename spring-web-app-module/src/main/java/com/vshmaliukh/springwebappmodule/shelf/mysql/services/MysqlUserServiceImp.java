package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlUserService;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;
import org.springframework.stereotype.Service;

@Service
public class MysqlUserServiceImp extends SqlUserService {

    public MysqlUserServiceImp(MysqlUserRepository mysqlUserRepository) {
        this.userRepository = mysqlUserRepository;
    }

}
