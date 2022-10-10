package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.MagazineEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlMagazineRepository extends JpaRepository<MagazineEntity, Integer>, ActionsWithItemEntity<MagazineEntity> {

    @Query("select max(entity.id) from MagazineEntity entity")
    Integer findMaxId();
}
