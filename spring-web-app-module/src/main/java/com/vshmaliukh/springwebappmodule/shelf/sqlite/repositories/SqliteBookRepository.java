package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.SqliteBookEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteBookRepository extends JpaRepository<SqliteBookEntity, Integer>, ActionsWithItemEntity<SqliteBookEntity> {

}
