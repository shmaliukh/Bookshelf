package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.convertors.ItemEntityConvertorProvider;
import com.vshmaliukh.spring_shelf_core.shelf.entities.ItemEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.ActionsWithItemEntity;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlItemService;
import com.vshmaliukh.spring_shelf_core.utils.MyLogUtil;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class SqlItemServiceImp implements SqlItemService {

    protected ItemEntityRepositoryActionsProvider itemEntityRepositoryActionsProvider;

    private static List<Item> convertListOfEntities(List<ItemEntity> entityList) {
        List<Item> itemList = new ArrayList<>();
        ItemEntityConvertor convertorByEntityClassType;
        for (ItemEntity entity : entityList) {
            convertorByEntityClassType = ItemEntityConvertorProvider.getConvertorByEntityClassType(entity.getClass());
            Item item = convertorByEntityClassType.getConvertedItemFromEntity(entity);
            itemList.add(item);
        }
        MyLogUtil.logDebug(SqlItemServiceImp.class, "convertListOfEntities(entityList: '{}') " +
                "// converted item list: '{}'", entityList, itemList);
        return itemList;
    }

    public void insertItemByUserId(Item item, Integer userId) {
        ActionsWithItemEntity repositoryActions = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(item.getClass());
        if (repositoryActions != null) {
            ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(item.getClass());
            ItemEntity itemEntity = convertorByItemClass.getConvertedEntityFromItem(item, userId);
            itemEntity.setUserId(userId);
            ItemEntity savedItemEntity = repositoryActions.save(itemEntity);
            MyLogUtil.logInfo(this, "user id: '{}' // item to insert: '{}' " +
                    "// saved item entity: '{}'", userId, item, savedItemEntity);
        } else {
            MyLogUtil.logWarn(itemEntityRepositoryActionsProvider, "problem to exist insertItemByUserId(item: '{}', userId: '{}') " +
                    "// repositoryActions == NULL", item, userId);
        }
    }

    public List<Item> readAllItemListByUserId(Integer userId) {
        List<ItemEntity> entityList = new ArrayList<>();
        for (ActionsWithItemEntity mysqlItemRepository : itemEntityRepositoryActionsProvider.getRepositoryActionList()) {
            List<ItemEntity> allPerTypeByUserId = mysqlItemRepository.findAllByUserId(userId);
            entityList.addAll(allPerTypeByUserId);
        }
        MyLogUtil.logDebug(this, "user id: '{}' // readAllItemListByUserId(userId: '{}') " +
                "// entity list from db: {}", userId, userId, entityList);
        List<Item> itemList = SqlItemServiceImp.convertListOfEntities(entityList);
        MyLogUtil.logDebug(this, "user id: '{}' // readAllItemListByUserId(userId: '{}') " +
                "// converted item list from db: {}", userId, userId, itemList);
        return itemList;
    }

    public <T extends Item> List<T> readItemListByClassAndUserId(Class<T> itemClassType, Integer userId) {
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(itemClassType);
        List entityListByClassType = repositoryByClassType.findAllByUserId(userId);
        MyLogUtil.logDebug(this, "user id: '{}' // readItemListByClassAndUserId(itemClassType: '{}', userId: '{}') " +
                "// entity list from db by class type: {}", userId, itemClassType, userId, entityListByClassType);
        List<T> itemList = SqlItemServiceImp.convertListOfEntities(entityListByClassType);
        MyLogUtil.logDebug(this, "user id: '{}' // readItemListByClassAndUserId(itemClassType: '{}', userId: '{}') " +
                "// converted item list by class type: {}", userId, itemClassType, userId, itemList);
        return itemList;
    }

    public void deleteItemByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClassType = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClassType.getConvertedEntityFromItem(item, userId);
        ActionsWithItemEntity repositoryActionsByClassType = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(itemClassType);
        repositoryActionsByClassType.deleteById(entityFromItem.getId());
        MyLogUtil.logInfo(this, "user id: '{}' // try to delete item: '{}' " +
                "// entity from item: '{}' ", userId, item, entityFromItem);
    }

    public void changeItemBorrowedStateByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        boolean currentBorrowedState = entityFromItem.isBorrowed();
        entityFromItem.setBorrowed(!currentBorrowedState);
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(itemClassType);
        ItemEntity itemEntity = repositoryByClassType.save(entityFromItem);
        MyLogUtil.logInfo(this, "user id: '{}' // item to change borrowed state: '{}' " +
                "// updated item entity: '{}'", userId, item, itemEntity);
    }

}
