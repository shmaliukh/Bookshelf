package com.vshmaliukh.springwebappmodule.shelf.repository_services;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import org.springframework.data.repository.query.Param;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.USER_NAME_COLUMN;

public interface ActionsWithUserEntity<T> {

//    void save(UserEntity userEntity);

    T save(UserEntity userEntity);

    T findByUserName(@Param(USER_NAME_COLUMN) String userName);

}
