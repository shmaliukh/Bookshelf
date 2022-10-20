package com.vshmaliukh.springbootshelfcore.shelf.sqlite.repositories;

import com.vshmaliukh.springbootshelfcore.shelf.entities.NewspaperEntity;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteNewspaperRepository extends JpaRepository<NewspaperEntity, Integer>, ActionsWithItemEntity<NewspaperEntity> {
}
