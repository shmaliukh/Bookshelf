package com.vshmaliukh.springwebappmodule.shelf.repository_services;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.ID_COLUMN;
import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.USER_NAME_COLUMN;

public interface ActionsWithUserEntity {

//    boolean existsByName(@Param(NAME_COLUMN) String name);
//
//    void removeAllById(@Param(ID_COLUMN) Integer id);
//
//    Integer findMaxId();
//
//    void save(UserEntity userEntity);
//
//    void deleteById(Integer userId);

    Optional<UserEntity> findById(@Param(ID_COLUMN) Integer id);

    UserEntity findByUserName(@Param(USER_NAME_COLUMN) String userName);

}
