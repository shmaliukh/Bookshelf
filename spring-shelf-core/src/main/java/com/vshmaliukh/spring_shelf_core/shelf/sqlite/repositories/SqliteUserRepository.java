package com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories;

import com.vshmaliukh.spring_shelf_core.shelf.entities.UserEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.ActionsWithUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteUserRepository extends JpaRepository<UserEntity, Integer>, ActionsWithUserEntity<UserEntity> {

}
