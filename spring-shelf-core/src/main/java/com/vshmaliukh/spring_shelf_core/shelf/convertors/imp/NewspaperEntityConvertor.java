package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.entities.NewspaperEntity;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

public class NewspaperEntityConvertor implements ItemEntityConvertor<Newspaper, NewspaperEntity> {

    @Override
    public Newspaper getConvertedItemFromEntity(NewspaperEntity entityToConvert) {
        return new Newspaper(entityToConvert.getId(),
                entityToConvert.getName(),
                entityToConvert.getPages(),
                entityToConvert.isBorrowed());
    }

    @Override
    public NewspaperEntity getConvertedEntityFromItem(Newspaper itemToConvert, Integer userId) {
        NewspaperEntity entity = new NewspaperEntity();
        entity.setId(itemToConvert.getId());
        entity.setUserId(userId);
        entity.setName(itemToConvert.getName());
        entity.setPages(itemToConvert.getPagesNumber());
        entity.setBorrowed(itemToConvert.isBorrowed());
        return entity;
    }
}
