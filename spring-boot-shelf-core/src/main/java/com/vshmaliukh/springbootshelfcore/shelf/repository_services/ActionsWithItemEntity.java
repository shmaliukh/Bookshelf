package com.vshmaliukh.springbootshelfcore.shelf.repository_services;

import com.vshmaliukh.springbootshelfcore.shelf.ConstantsForDataBase;
import com.vshmaliukh.springbootshelfcore.shelf.entities.ItemEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionsWithItemEntity<T extends ItemEntity> {

    List<T> findAllByUserId(@Param(ConstantsForDataBase.USER_ID_COLUMN) Integer userId);

    void deleteById(@Param(ConstantsForDataBase.USER_ID_COLUMN) Integer itemId);

    T save(T itemEntity);

}
