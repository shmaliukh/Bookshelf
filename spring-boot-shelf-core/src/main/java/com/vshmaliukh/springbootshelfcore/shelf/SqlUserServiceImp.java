package com.vshmaliukh.springbootshelfcore.shelf;

import com.vshmaliukh.springbootshelfcore.shelf.repository_services.ActionsWithUserEntity;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.SqlUserService;
import com.vshmaliukh.springbootshelfcore.shelf.entities.UserEntity;

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
