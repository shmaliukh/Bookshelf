package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.entities.UserEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.ActionsWithUserEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlUserService;
import com.vshmaliukh.spring_shelf_core.utils.MyLogUtil;

public abstract class SqlUserServiceImp implements SqlUserService {

    protected ActionsWithUserEntity<UserEntity> userRepository;

    @Override
    public void insertUser(String userName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        UserEntity savedEntity = userRepository.save(userEntity);
        MyLogUtil.logInfo(this, "userName: '{}' // saved entity: '{}'", userName, savedEntity);
    }

    @Override
    public Integer readUserIdByName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity != null) {
            return userEntity.getId();
        }
        MyLogUtil.logWarn(this, "userName: '{}' // not find entity by user name // 'readUserIdByName' return value is NULL", userName);
        return null;
    }
}
