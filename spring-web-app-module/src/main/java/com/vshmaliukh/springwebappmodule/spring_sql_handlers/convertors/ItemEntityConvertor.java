package com.vshmaliukh.springwebappmodule.spring_sql_handlers.convertors;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.ItemEntity;
import org.vshmaliukh.shelf.literature_items.Item;

public interface ItemEntityConvertor<I extends Item, E extends ItemEntity> {

    I getConvertedItemFromEntity(E entityToConvert);
    E getConvertedEntityFromItem(I itemToConvert, Integer userId);

}
