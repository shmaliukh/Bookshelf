package com.vshmaliukh.springbootshelfcore.shelf.convertors;

import com.vshmaliukh.springbootshelfcore.shelf.entities.ItemEntity;
import org.vshmaliukh.shelf.literature_items.Item;

public interface ItemEntityConvertor<I extends Item, E extends ItemEntity> {

    I getConvertedItemFromEntity(E entityToConvert);
    E getConvertedEntityFromItem(I itemToConvert, Integer userId);

}
