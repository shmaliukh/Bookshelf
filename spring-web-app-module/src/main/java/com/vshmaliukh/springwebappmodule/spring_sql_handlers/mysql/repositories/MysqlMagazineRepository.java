package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.repositories;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.MagazineEntity;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.repository_services.ActionsWithItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlMagazineRepository extends JpaRepository<MagazineEntity, Integer>, ActionsWithItemEntity<MagazineEntity> {

}
