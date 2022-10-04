package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqliteBookRepository extends JpaRepository<BookEntity, Integer> {
}
