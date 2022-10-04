package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.NewspaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqliteNewspaperRepository extends JpaRepository<NewspaperEntity, Integer> {
}
