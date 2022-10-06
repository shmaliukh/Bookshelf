package com.vshmaliukh.springwebappmodule.shelf.itemEntityConvertors;

public interface ItemEntityConvertor<I, E> {

    I getConvertedItemFromEntity(E entityToConvert);
    E getConvertedEntityFromItem(I itemToConvert);

}
