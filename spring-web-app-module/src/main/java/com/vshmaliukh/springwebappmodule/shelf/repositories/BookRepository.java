package com.vshmaliukh.springwebappmodule.shelf.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer>  {

    boolean existsByName(@Param(NAME_COLUMN) String name);

    List<BookEntity> findByName(@Param(NAME_COLUMN) String name);

    List<BookEntity> findAllByUserId(@Param(USER_ID_COLUMN) Integer userId);

    void removeAllById(@Param(ID_COLUMN) Integer id);

    @Query("select max(b.id) from BookEntity b")
    Integer findMaxId();

}