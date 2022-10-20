package com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories;

import com.vshmaliukh.springbootshelfcore.shelf.entities.ComicsEntity;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlComicsRepository extends JpaRepository<ComicsEntity, Integer>, ActionsWithItemEntity<ComicsEntity> {

}
