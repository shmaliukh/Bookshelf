package com.vshmaliukh.springwebappmodule.shelf.repositories;

import com.vshmaliukh.springwebappmodule.shelf.entities.ComicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicsRepository extends JpaRepository<ComicsEntity, Integer> {
}
