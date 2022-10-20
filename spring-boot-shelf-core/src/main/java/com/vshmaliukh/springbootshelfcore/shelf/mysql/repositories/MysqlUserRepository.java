package com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories;

import com.vshmaliukh.springbootshelfcore.shelf.entities.UserEntity;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.ActionsWithUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MysqlUserRepository extends JpaRepository<UserEntity, Integer>, ActionsWithUserEntity<UserEntity> {

    UserEntity save(UserEntity userEntity);

}
