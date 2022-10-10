package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.convertors.BookEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.*;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ItemService;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public final class ItemEntityServiceProvider {

    public static Set<ItemService> itemEntityServiceSet;
    private static final Map<Class, ItemService> itemClassServiceMap = new ConcurrentHashMap<>();
    private static final Map<Class, ActionsWithItemEntity> mysqlItemRepositoryMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Item>, ItemEntityConvertor<? extends Item, ?>> itemEntityConvertorMap = new ConcurrentHashMap<>();

    static {
        itemEntityConvertorMap.put(Book.class, new BookEntityConvertor());
        itemClassServiceMap.put(Book.class, new MysqlItemServiceImp(null));

        itemEntityServiceSet = Collections.unmodifiableSet(new HashSet<>(itemClassServiceMap.values()));
    }

    final MysqlUserRepository mysqlUserRepository;
    final MysqlBookRepository mysqlBookRepository;
    final MysqlMagazineRepository mysqlMagazineRepository;
    final MysqlNewspaperRepository mysqlNewspaperRepository;
    final MysqlComicsRepository mysqlComicsRepository;

    private ItemEntityServiceProvider(MysqlBookRepository mysqlBookRepository,
                                      MysqlUserRepository mysqlUserRepository,
                                      MysqlMagazineRepository mysqlMagazineRepository,
                                      MysqlNewspaperRepository mysqlNewspaperRepository,
                                      MysqlComicsRepository mysqlComicsRepository) {
        this.mysqlBookRepository = mysqlBookRepository;
        this.mysqlUserRepository = mysqlUserRepository;
        this.mysqlMagazineRepository = mysqlMagazineRepository;
        this.mysqlNewspaperRepository = mysqlNewspaperRepository;
        this.mysqlComicsRepository = mysqlComicsRepository;

        initClassTypeRepositoryMap(mysqlBookRepository, mysqlMagazineRepository, mysqlNewspaperRepository, mysqlComicsRepository);
    }

    private static void initClassTypeRepositoryMap(MysqlBookRepository mysqlBookRepository, MysqlMagazineRepository mysqlMagazineRepository, MysqlNewspaperRepository mysqlNewspaperRepository, MysqlComicsRepository mysqlComicsRepository) {
        mysqlItemRepositoryMap.put(Book.class, mysqlBookRepository);
        mysqlItemRepositoryMap.put(Magazine.class, mysqlMagazineRepository);
        mysqlItemRepositoryMap.put(Comics.class, mysqlComicsRepository);
        mysqlItemRepositoryMap.put(Newspaper.class, mysqlNewspaperRepository);
    }

    public ActionsWithItemEntity getMysqlRepositoryByClassType(Class itemClassType) {
        return mysqlItemRepositoryMap.getOrDefault(itemClassType, null);
    }

    public List<ActionsWithItemEntity> getMysqlRepositoryList() {
        return new ArrayList<>(mysqlItemRepositoryMap.values());
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
