package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.ID_COLUMN;
import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.USER_NAME_COLUMN;


@Repository
public interface MysqlUserRepository extends JpaRepository<UserEntity, Integer> {



    List itemList = new ArrayList<>();

    Optional<UserEntity> findById(@Param(ID_COLUMN) Integer id);

    UserEntity findByUserName(@Param(USER_NAME_COLUMN) String userName);

}
