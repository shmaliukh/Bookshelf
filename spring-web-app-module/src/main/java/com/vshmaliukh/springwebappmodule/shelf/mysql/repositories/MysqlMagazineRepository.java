package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlMagazineRepository extends JpaRepository<MagazineEntity, Integer> {
}
