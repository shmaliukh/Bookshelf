package com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories;

import com.vshmaliukh.spring_shelf_core.shelf.entities.NewspaperEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlNewspaperRepository extends JpaRepository<NewspaperEntity, Integer>, ActionsWithItemEntity<NewspaperEntity> {

}
