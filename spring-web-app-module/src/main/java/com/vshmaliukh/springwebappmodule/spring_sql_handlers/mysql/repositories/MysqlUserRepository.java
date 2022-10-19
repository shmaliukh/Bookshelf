package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.repositories;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.repository_services.ActionsWithUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MysqlUserRepository extends JpaRepository<UserEntity, Integer>, ActionsWithUserEntity<UserEntity> {

    UserEntity save(UserEntity userEntity);

}
