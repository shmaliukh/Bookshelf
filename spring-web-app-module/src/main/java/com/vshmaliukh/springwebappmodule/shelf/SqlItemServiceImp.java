package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.entities.ItemEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.SqlItemService;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertorProvider;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import org.jetbrains.annotations.NotNull;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SqlItemServiceImp implements SqlItemService {

    protected ItemEntityRepositoryActionsProvider itemEntityRepositoryActionsProvider;

    @NotNull
    private static List<Item> convertListOfEntities(List<ItemEntity> entityList) {// todo refactor
        return entityList.stream()
                .map(o -> ItemEntityConvertorProvider.getConvertorByEntityClassType(o.getClass()).getConvertedItemFromEntity(o))
                .collect(Collectors.toList());
    }

    public void insertItemByUserId(Item item, Integer userId) {
        ActionsWithItemEntity repository = itemEntityRepositoryActionsProvider.getMysqlRepositoryActionByClassType(item.getClass());
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(item.getClass());
        ItemEntity itemEntity = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        itemEntity.setUserId(userId);
        repository.save(itemEntity);
    }

    public List<Item> readAllItemListByUserId(Integer userId) {
        List<ItemEntity> entityList = new ArrayList<>();
        for (ActionsWithItemEntity mysqlItemRepository : itemEntityRepositoryActionsProvider.getMysqlRepositoryActionList()) {
            List<ItemEntity> allPerTypeByUserId = mysqlItemRepository.findAllByUserId(userId);
            entityList.addAll(allPerTypeByUserId);
        }
        return SqlItemServiceImp.convertListOfEntities(entityList);
    }

    public <T extends Item> List<T> readItemListByClassAndUserId(Class<T> itemClassType, Integer userId) {
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getMysqlRepositoryActionByClassType(itemClassType);
        List entityListByClassType = repositoryByClassType.findAllByUserId(userId);
        List<T> itemList = SqlItemServiceImp.convertListOfEntities(entityListByClassType);
        return itemList;
    }

    public void deleteItemByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClassType = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClassType.getConvertedEntityFromItem(item, userId);
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getMysqlRepositoryActionByClassType(itemClassType);
        repositoryByClassType.deleteById(entityFromItem.getId());
    }

    public void changeItemBorrowedStateByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        entityFromItem.setBorrowed(!entityFromItem.isBorrowed());
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryActionsProvider.getMysqlRepositoryActionByClassType(itemClassType);
        repositoryByClassType.save(entityFromItem);// fixme use update function
    }

}
