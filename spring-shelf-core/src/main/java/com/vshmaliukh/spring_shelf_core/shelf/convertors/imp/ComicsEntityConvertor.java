package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.entities.ComicsEntity;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;

public class ComicsEntityConvertor implements ItemEntityConvertor<Comics, ComicsEntity> {

    @Override
    public Comics getConvertedItemFromEntity(ComicsEntity entityToConvert) {
        return new Comics(entityToConvert.getId(),
                entityToConvert.getName(),
                entityToConvert.getPages(),
                entityToConvert.isBorrowed(),
                entityToConvert.getPublisher());
    }

    @Override
    public ComicsEntity getConvertedEntityFromItem(Comics itemToConvert, Integer userId) {
        ComicsEntity entity = new ComicsEntity();
        entity.setId(itemToConvert.getId());
        entity.setUserId(userId);
        entity.setName(itemToConvert.getName());
        entity.setPages(itemToConvert.getPagesNumber());
        entity.setBorrowed(itemToConvert.isBorrowed());
        entity.setPublisher(itemToConvert.getPublisher());
        return entity;
    }
}
