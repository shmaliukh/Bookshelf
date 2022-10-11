package com.vshmaliukh.springwebappmodule.shelf.repository_services;

import com.vshmaliukh.springwebappmodule.shelf.entities.ItemEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

public interface ActionsWithItemEntity<T extends ItemEntity> {

    List<T> findAllByUserId(@Param(USER_ID_COLUMN) Integer userId);

//    void save(T itemEntity);

    T save(T itemEntity);

    void deleteById(Integer itemId);

}
