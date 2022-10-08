package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityServiceProvider;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.entities.ItemEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithDatabaseEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ItemService;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MysqlItemServiceImp implements ItemService {

    final
    ItemEntityServiceProvider itemEntityServiceProvider;

    public MysqlItemServiceImp(ItemEntityServiceProvider itemEntityServiceProvider) {
        this.itemEntityServiceProvider = itemEntityServiceProvider;
    }

    @Override
    public void addItem(Item item, Integer userId) {
        ItemEntityConvertor convertorByItemClass = ItemEntityServiceProvider.getConvertorByItemClass(item.getClass());
        ItemEntity entity = convertorByItemClass.getConvertedEntityFromItem(item, userId);

        // todo
        ActionsWithDatabaseEntity repository = itemEntityServiceProvider.getMysqlRepositoryByClassType(item.getClass());
//        repository
        repository.save(entity);
    }

    @Override
    public Item readItem() {
        return null;
    }

    @Override
    public void deleteItemByUserId(Item item, Integer userId) {
        ActionsWithDatabaseEntity repository = itemEntityServiceProvider.getMysqlRepositoryByClassType(item.getClass());
        repository.deleteById(userId); // todo
    }


    @Override
    public void changeBorrowedState(Item item, Integer userId) {
        ItemEntityConvertor convertorByItemClass = ItemEntityServiceProvider.getConvertorByItemClass(item.getClass());
        ItemEntity convertedEntityFromItem = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        convertedEntityFromItem.setBorrowed(!convertedEntityFromItem.isBorrowed());
        ActionsWithDatabaseEntity repository = itemEntityServiceProvider.getMysqlRepositoryByClassType(item.getClass());
        repository.save(convertedEntityFromItem);// fixme
    }

    @Override
    public List<Item> readAllItemsByUserId(Integer userId) {
        List<ItemEntity> entityList = new ArrayList<>();
        for (ActionsWithDatabaseEntity actionsWithDatabase : itemEntityServiceProvider.getMysqlRepositoryList()) {
            List<ItemEntity> allByUserId = actionsWithDatabase.findAllByUserId(userId);
            entityList.addAll(allByUserId);
        }

        return entityList.stream()
                .map(o -> ItemEntityServiceProvider.getConvertorByItemClass(o.getClass()).getConvertedItemFromEntity(o))
                .collect(Collectors.toList());
    }
}
