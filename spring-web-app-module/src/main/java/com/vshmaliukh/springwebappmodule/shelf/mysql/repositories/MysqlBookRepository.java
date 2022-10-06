package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.NAME_COLUMN;

@Repository
public interface MysqlBookRepository extends JpaRepository<BookEntity, Integer> {

    boolean existsByName(@Param(NAME_COLUMN) String name);

    List<BookEntity> findByName(@Param(NAME_COLUMN) String name);

    @Query("select max(b.id) from BookEntity b")
    Integer findMaxId();

}