package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.entities.UserEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.ActionsWithUserEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlUserService;

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