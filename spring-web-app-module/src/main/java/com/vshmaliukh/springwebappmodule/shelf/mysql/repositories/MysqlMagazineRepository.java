package com.vshmaliukh.springwebappmodule.shelf.mysql.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlMagazineRepository extends JpaRepository<MagazineEntity, Integer> {
}
