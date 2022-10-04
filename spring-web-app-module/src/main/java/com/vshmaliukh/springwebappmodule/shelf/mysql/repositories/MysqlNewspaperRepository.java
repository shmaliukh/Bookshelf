package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.NewspaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlNewspaperRepository extends JpaRepository<NewspaperEntity, Integer> {
}
