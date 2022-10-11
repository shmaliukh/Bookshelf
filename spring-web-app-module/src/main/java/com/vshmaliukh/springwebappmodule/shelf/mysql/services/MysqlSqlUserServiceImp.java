package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.SqlUserService;
import org.springframework.stereotype.Service;

@Service
public class MysqlSqlUserServiceImp implements SqlUserService {

    final MysqlUserRepository mysqlUserRepository;

    public MysqlSqlUserServiceImp(MysqlUserRepository mysqlUserRepository) {
        this.mysqlUserRepository = mysqlUserRepository;
    }

    @Override
    public void insertUser(String userName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        mysqlUserRepository.save(userEntity);
    }

    @Override
    public Integer readUserIdByName(String userName) {
        UserEntity userEntity = mysqlUserRepository.findByUserName(userName);
        if(userEntity != null) {
            Integer id = userEntity.getId();
            return id;
        }
        return null;
    }

}
