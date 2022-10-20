package com.vshmaliukh.springbootshelfcore.shelf;

import com.vshmaliukh.springbootshelfcore.shelf.entities.ItemEntity;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.SqlItemService;
import com.vshmaliukh.springbootshelfcore.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springbootshelfcore.shelf.convertors.ItemEntityConvertorProvider;
import com.vshmaliukh.springbootshelfcore.shelf.repository_services.ActionsWithItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class SqlItemServiceImp implements SqlItemService {

    protected ItemEntityRepositoryActionsProvider itemEntityRepositoryActionsProvider;

    @NotNull
    private static List<Item> convertListOfEntities(List<ItemEntity> entityList) {
        List<Item> itemList = new ArrayList<>();
        ItemEntityConvertor convertorByEntityClassType;
        for (ItemEntity entity : entityList) {
            convertorByEntityClassType = ItemEntityConvertorProvider.getConvertorByEntityClassType(entity.getClass());
            Item item = convertorByEntityClassType.getConvertedItemFromEntity(entity);
            itemList.add(item);
        }
        return itemList;
    }

    public void insertItemByUserId(Item item, Integer userId) {
        ActionsWithItemEntity repositoryActions = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(item.getClass());
        if (repositoryActions != null) {
            ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(item.getClass());
            ItemEntity itemEntity = convertorByItemClass.getConvertedEntityFromItem(item, userId);
            itemEntity.setUserId(userId);
            repositoryActions.save(itemEntity);
        } else {
            log.error("[" + itemEntityRepositoryActionsProvider.getClass().getSimpleName() + "] err: " +
                    "problem to exist insertItemByUserId(item: " + item + " , userId: " + userId + ") // repositoryActions == null");
        }
    }

    public List<Item> readAllItemListByUserId(Integer userId) {
        List<ItemEntity> entityList = new ArrayList<>();
        for (ActionsWithItemEntity mysqlItemRepository : itemEntityRepositoryActionsProvider.getRepositoryActionList()) {
            List<ItemEntity> allPerTypeByUserId = mysqlItemRepository.findAllByUserId(userId);
            entityList.addAll(allPerTypeByUserId);
        }
        return SqlItemServiceImp.convertListOfEntities(entityList);
    }

    public <T extends Item> List<T> readItemListByClassAndUserId(Class<T> itemClassType, Integer userId) {
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(itemClassType);
        List entityListByClassType = repositoryByClassType.findAllByUserId(userId);
        List<T> itemList = SqlItemServiceImp.convertListOfEntities(entityListByClassType);
        return itemList;
    }

    public void deleteItemByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClassType = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClassType.getConvertedEntityFromItem(item, userId);
        ActionsWithItemEntity repositoryActionsByClassType = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(itemClassType);
        repositoryActionsByClassType.deleteById(entityFromItem.getId());
    }

    public void changeItemBorrowedStateByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        entityFromItem.setBorrowed(!entityFromItem.isBorrowed());
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getRepositoryActionByClassType(itemClassType);
        repositoryByClassType.save(entityFromItem);
    }

}
