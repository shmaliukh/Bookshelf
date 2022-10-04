package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqliteMagazineRepository extends JpaRepository<MagazineEntity, Integer> {
}
