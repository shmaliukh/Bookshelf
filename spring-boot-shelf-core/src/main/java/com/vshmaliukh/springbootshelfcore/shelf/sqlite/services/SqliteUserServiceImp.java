package com.vshmaliukh.springbootshelfcore.shelf.sqlite.services;

import com.vshmaliukh.springbootshelfcore.shelf.SqlUserServiceImp;
import com.vshmaliukh.springbootshelfcore.shelf.sqlite.repositories.SqliteUserRepository;
import org.springframework.stereotype.Service;

@Service
public class SqliteUserServiceImp extends SqlUserServiceImp {

    public SqliteUserServiceImp(SqliteUserRepository sqliteUserRepository) {
        this.userRepository = sqliteUserRepository;
    }

}
