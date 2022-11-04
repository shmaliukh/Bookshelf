package com.vshmaliukh.spring_shelf_core.shelf.convertors;

import com.vshmaliukh.spring_shelf_core.shelf.entities.ItemEntity;
import org.vshmaliukh.shelf.literature_items.Item;

public interface ItemEntityConvertor<I extends Item, E extends ItemEntity> {

    I getConvertedItemFromEntity(E entityToConvert);
    E getConvertedEntityFromItem(I itemToConvert, Integer userId);

}
