package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityRepositoryProvider;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertorProvider;
import com.vshmaliukh.springwebappmodule.shelf.entities.ItemEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ItemService;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class MysqlItemServiceImp implements ItemService {

    ItemEntityRepositoryProvider itemEntityRepositoryProvider;

    @Autowired
    public void setItemEntityRepositoryProvider(ItemEntityRepositoryProvider itemEntityRepositoryProvider) {
        this.itemEntityRepositoryProvider = itemEntityRepositoryProvider;
    }

    @Override
    public void insertItemByUserId(Item item, Integer userId) {
        ActionsWithItemEntity repository = itemEntityRepositoryProvider.getMysqlRepositoryByClassType(item.getClass());
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(item.getClass());
        ItemEntity itemEntity = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        itemEntity.setUserId(userId);
        repository.save(itemEntity);
    }

    @Override
    public List<Item> readAllItemListByUserId(Integer userId) {
        List<ItemEntity> entityList = new ArrayList<>();
        for (ActionsWithItemEntity mysqlItemRepository : itemEntityRepositoryProvider.getMysqlRepositoryList()) {
            List<ItemEntity> allPerTypeByUserId = mysqlItemRepository.findAllByUserId(userId);
            entityList.addAll(allPerTypeByUserId);
        }
        return convertListOfEntities(entityList);
    }

    @NotNull
    private static List<Item> convertListOfEntities(List<ItemEntity> entityList) {// todo refactor
        return entityList.stream()
                .map(o -> ItemEntityConvertorProvider.getConvertorByEntityClassType(o.getClass()).getConvertedItemFromEntity(o))
                .collect(Collectors.toList());
    }

    @Override
    public <T extends Item> List<T> readItemListByClassAndUserId(Class<T> itemClassType, Integer userId) {
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryProvider.getMysqlRepositoryByClassType(itemClassType);
        List entityListByClassType = repositoryByClassType.findAllByUserId(userId);
        List<T> itemList = convertListOfEntities(entityListByClassType);
        return itemList;
    }

    @Override
    public void deleteItemByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClassType = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClassType.getConvertedEntityFromItem(item, userId);
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryProvider.getMysqlRepositoryByClassType(itemClassType);
        repositoryByClassType.deleteById(entityFromItem.getId());
    }

    @Override
    public void changeItemBorrowedStateByUserId(Item item, Integer userId) {
        Class<? extends Item> itemClassType = item.getClass();
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorProvider.getConvertorByItemClassType(itemClassType);
        ItemEntity entityFromItem = convertorByItemClass.getConvertedEntityFromItem(item, userId);
        entityFromItem.setBorrowed(!entityFromItem.isBorrowed());
        ActionsWithItemEntity repositoryByClassType = itemEntityRepositoryProvider.getMysqlRepositoryByClassType(itemClassType);
        repositoryByClassType.save(entityFromItem);// fixme
    }

}
