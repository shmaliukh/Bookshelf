package org.vshmaliukh.services.save_read_services;

import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface SaveReadItems {

    void saveItemListToDB(List<Item> listToSave);

    List<Item> readItemList();

    <T extends Item> List<T> readItemsByClass(Class<T> classType);

}
