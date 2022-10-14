package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithUserEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.SqlUserService;

public abstract class SqlUserServiceImp implements SqlUserService {

    protected ActionsWithUserEntity<UserEntity> userRepository;

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
            return userEntity.getId();
        }
        return null;
    }
}
