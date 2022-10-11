package com.vshmaliukh.springwebappmodule.shelf.sqlite.services;

import com.vshmaliukh.springwebappmodule.shelf.SqlUserServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteUserRepository;
import org.springframework.stereotype.Service;

@Service
public class SqliteUserServiceImp extends SqlUserServiceImp {

    public SqliteUserServiceImp(SqliteUserRepository sqliteUserRepository) {
        this.userRepository = sqliteUserRepository;
    }

}
