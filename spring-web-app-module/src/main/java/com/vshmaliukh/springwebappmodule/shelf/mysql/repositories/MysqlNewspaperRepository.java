package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.NewspaperEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlNewspaperRepository extends JpaRepository<NewspaperEntity, Integer>, ActionsWithItemEntity<NewspaperEntity> {

    @Query("select max(entity.id) from NewspaperEntity entity")
    Integer findMaxId();
}
