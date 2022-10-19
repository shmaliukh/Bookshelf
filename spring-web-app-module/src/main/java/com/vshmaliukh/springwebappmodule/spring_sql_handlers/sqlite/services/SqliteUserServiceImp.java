package com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.SqlUserServiceImp;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.repositories.SqliteUserRepository;
import org.springframework.stereotype.Service;

@Service
public class SqliteUserServiceImp extends SqlUserServiceImp {

    public SqliteUserServiceImp(SqliteUserRepository sqliteUserRepository) {
        this.userRepository = sqliteUserRepository;
    }

}
