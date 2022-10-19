package com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.ComicsEntity;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteComicsRepository extends JpaRepository<ComicsEntity, Integer>, ActionsWithItemEntity<ComicsEntity> {

}
