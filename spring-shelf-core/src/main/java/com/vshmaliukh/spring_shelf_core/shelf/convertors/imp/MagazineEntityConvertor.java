package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.entities.MagazineEntity;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;

public class MagazineEntityConvertor implements ItemEntityConvertor<Magazine, MagazineEntity> {

    @Override
    public Magazine getConvertedItemFromEntity(MagazineEntity entityToConvert) {
        return new Magazine(entityToConvert.getId(),
                entityToConvert.getName(),
                entityToConvert.getPages(),
                entityToConvert.isBorrowed());
    }

    @Override
    public MagazineEntity getConvertedEntityFromItem(Magazine itemToConvert, Integer userId) {
        MagazineEntity entity = new MagazineEntity();
        entity.setId(itemToConvert.getId());
        entity.setUserId(userId);
        entity.setName(itemToConvert.getName());
        entity.setPages(itemToConvert.getPagesNumber());
        entity.setBorrowed(itemToConvert.isBorrowed());
        return entity;
    }
}
