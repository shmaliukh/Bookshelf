package com.vshmaliukh.springwebappmodule.spring_sql_handlers.repository_services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.ConstantsForDataBase;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.ItemEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionsWithItemEntity<T extends ItemEntity> {

    List<T> findAllByUserId(@Param(ConstantsForDataBase.USER_ID_COLUMN) Integer userId);

    void deleteById(@Param(ConstantsForDataBase.USER_ID_COLUMN) Integer itemId);

    T save(T itemEntity);

    //    void save(T itemEntity);

}
