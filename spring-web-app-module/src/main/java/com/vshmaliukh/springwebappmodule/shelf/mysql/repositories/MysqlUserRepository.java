package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlUserRepository extends JpaRepository<UserEntity, Integer> {
}
