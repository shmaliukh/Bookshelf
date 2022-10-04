package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.ComicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlComicsRepository extends JpaRepository<ComicsEntity, Integer> {
}
