package com.vshmaliukh.springwebappmodule.shelf.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends JpaRepository<MagazineEntity, Integer> {
}
