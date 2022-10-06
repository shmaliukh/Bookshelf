package com.vshmaliukh.springwebappmodule.shelf.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.USER_NAME_COLUMN;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserName(@Param(USER_NAME_COLUMN) String userName);

}
