package com.vshmaliukh.springwebappmodule.spring_sql_handlers.repository_services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.UserEntity;
import org.springframework.data.repository.query.Param;

import static com.vshmaliukh.springwebappmodule.spring_sql_handlers.ConstantsForDataBase.USER_NAME_COLUMN;

public interface ActionsWithUserEntity<T> {

//    void save(UserEntity userEntity);

    T save(UserEntity userEntity);

    T findByUserName(@Param(USER_NAME_COLUMN) String userName);

}
