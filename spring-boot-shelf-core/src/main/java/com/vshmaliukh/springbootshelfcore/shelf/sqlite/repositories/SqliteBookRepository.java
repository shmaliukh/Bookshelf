package com.vshmaliukh.springbootshelfcore.shelf.sqlite.repositories;

import com.vshmaliukh.springbootshelfcore.shelf.entities.BookEntity;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteBookRepository extends JpaRepository<BookEntity, Integer>, ActionsWithItemEntity<BookEntity> {

}
