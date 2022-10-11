package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ItemEntityRepositoryProvider {

    protected ActionsWithItemEntity bookRepository;
    protected ActionsWithItemEntity magazineRepository;
    protected ActionsWithItemEntity newspaperRepository;
    protected ActionsWithItemEntity comicsRepository;

    protected Map<Class, ActionsWithItemEntity> mysqlItemRepositoryMap = new ConcurrentHashMap<>();

    @PostConstruct
    protected void initClassTypeRepositoryMap() {
        mysqlItemRepositoryMap.put(Book.class, bookRepository);
        mysqlItemRepositoryMap.put(Magazine.class, magazineRepository);
        mysqlItemRepositoryMap.put(Comics.class, comicsRepository);
        mysqlItemRepositoryMap.put(Newspaper.class, newspaperRepository);
    }

    public ActionsWithItemEntity getMysqlRepositoryByClassType(Class itemClassType) {
        return mysqlItemRepositoryMap.getOrDefault(itemClassType, null);
    }

    public List<ActionsWithItemEntity> getMysqlRepositoryList() {
        return new ArrayList<>(mysqlItemRepositoryMap.values());
    }
}
