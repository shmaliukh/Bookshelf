package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlBookRepository extends JpaRepository<BookEntity, Integer> {
}
