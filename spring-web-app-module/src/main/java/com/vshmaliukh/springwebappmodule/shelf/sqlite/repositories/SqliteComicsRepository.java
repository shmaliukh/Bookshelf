package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.ComicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqliteComicsRepository extends JpaRepository<ComicsEntity, Integer> {
}
