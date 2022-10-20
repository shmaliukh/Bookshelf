package com.vshmaliukh.spring_shelf_core.shelf.sqlite.services;

import com.vshmaliukh.spring_shelf_core.shelf.SqlUserServiceImp;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories.SqliteUserRepository;
import org.springframework.stereotype.Service;

@Service
public class SqliteUserServiceImp extends SqlUserServiceImp {

    public SqliteUserServiceImp(SqliteUserRepository sqliteUserRepository) {
        this.userRepository = sqliteUserRepository;
    }

}
