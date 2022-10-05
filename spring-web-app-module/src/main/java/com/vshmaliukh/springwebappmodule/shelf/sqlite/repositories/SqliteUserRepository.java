package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteUserRepository extends JpaRepository<UserEntity, Integer> {
}
