package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;

public abstract class SqlUserService implements com.vshmaliukh.springwebappmodule.shelf.repository_services.SqlUserService {

    protected MysqlUserRepository userRepository;

    @Override
    public void insertUser(String userName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userRepository.save(userEntity);
    }

    @Override
    public Integer readUserIdByName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity != null) {
            Integer id = userEntity.getId();
            return id;
        }
        return null;
    }
}
