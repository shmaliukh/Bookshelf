package com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqliteMagazineRepository extends JpaRepository<MagazineEntity, Integer> {
}
