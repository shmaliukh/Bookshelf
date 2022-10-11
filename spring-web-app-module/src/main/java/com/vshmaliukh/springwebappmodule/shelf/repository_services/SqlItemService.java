package com.vshmaliukh.springwebappmodule.shelf.repository_services;

import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface SqlItemService {

    void insertItemByUserId(Item item, Integer userId);

    List<Item> readAllItemListByUserId(Integer userId);

    <T extends Item> List<T> readItemListByClassAndUserId(Class<T> classType, Integer userId);

    void deleteItemByUserId(Item item, Integer userId);

    void changeItemBorrowedStateByUserId(Item item, Integer userId);
}
