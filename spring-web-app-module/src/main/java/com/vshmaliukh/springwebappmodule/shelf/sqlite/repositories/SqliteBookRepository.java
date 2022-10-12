package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteBookRepository extends JpaRepository<BookEntity, Integer>, ActionsWithItemEntity<BookEntity> {

}
