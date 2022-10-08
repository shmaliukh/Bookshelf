package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.convertors.BookEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithDatabaseEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ItemService;
import org.springframework.stereotype.Component;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class ItemEntityServiceProvider {

    final
    MysqlBookRepository mysqlBookRepository;
    final
    MysqlUserRepository mysqlUserRepository;

    private ItemEntityServiceProvider(MysqlBookRepository mysqlBookRepository, MysqlUserRepository mysqlUserRepository) {
        this.mysqlBookRepository = mysqlBookRepository;
        this.mysqlUserRepository = mysqlUserRepository;

        mysqlItemRepositoryMap.put(Book.class, mysqlBookRepository);
    }


    public ActionsWithDatabaseEntity getMysqlRepositoryByClassType(Class itemClassType){
        return mysqlItemRepositoryMap.getOrDefault(itemClassType,null);
    }

    public List<ActionsWithDatabaseEntity> getMysqlRepositoryList(){
        return new ArrayList<>(mysqlItemRepositoryMap.values());
    }



    public static Set<ItemService> itemEntityServiceSet;
    private static final Map<Class, ItemService> itemClassServiceMap = new ConcurrentHashMap<>();
    private static final Map<Class, ActionsWithDatabaseEntity> mysqlItemRepositoryMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Item>, ItemEntityConvertor<? extends Item, ?>> itemEntityConvertorMap = new ConcurrentHashMap<>();



    static {
        itemEntityConvertorMap.put(Book.class, new BookEntityConvertor());
        itemClassServiceMap.put(Book.class, new MysqlItemServiceImp(null));


        itemEntityServiceSet = Collections.unmodifiableSet(new HashSet<>(itemClassServiceMap.values()));
    }

    public static Set<ItemService> getItemEntityServiceSet() {
        return itemEntityServiceSet;
    }

    public static <T extends Item> ItemService getServiceByItemClass(Class<T> itemClassType) {
        return itemClassServiceMap.get(itemClassType);
    }

    public static ItemEntityConvertor getConvertorByItemClass(Class itemClassType) {
        return itemEntityConvertorMap.getOrDefault(itemClassType, null);
    }
}
