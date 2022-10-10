package com.vshmaliukh.springwebappmodule.shelf.repository_services;

import com.vshmaliukh.springwebappmodule.shelf.entities.ItemEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

public interface ActionsWithItemEntity<T extends ItemEntity> {

    boolean existsByName(@Param(NAME_COLUMN) String name);

    List<T> findByName(@Param(NAME_COLUMN) String name);

    List<T> findAllByUserId(@Param(USER_ID_COLUMN) Integer userId);

    void removeAllById(@Param(ID_COLUMN) Integer id);

    Integer findMaxId();

    void save(T itemEntity);

    void deleteById(Integer itemId);

}
