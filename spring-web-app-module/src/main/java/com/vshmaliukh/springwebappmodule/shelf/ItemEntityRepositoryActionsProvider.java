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

public abstract class ItemEntityRepositoryActionsProvider {

    protected ActionsWithItemEntity bookRepositoryActions;
    protected ActionsWithItemEntity magazineRepositoryActions;
    protected ActionsWithItemEntity newspaperRepositoryActions;
    protected ActionsWithItemEntity comicsRepositoryActions;

    protected Map<Class, ActionsWithItemEntity> mysqlItemRepositoryActionMap = new ConcurrentHashMap<>();

    @PostConstruct
    protected void initClassTypeRepositoryActionMap() {
        mysqlItemRepositoryActionMap.put(Book.class, bookRepositoryActions);
        mysqlItemRepositoryActionMap.put(Magazine.class, magazineRepositoryActions);
        mysqlItemRepositoryActionMap.put(Comics.class, comicsRepositoryActions);
        mysqlItemRepositoryActionMap.put(Newspaper.class, newspaperRepositoryActions);
    }

    public ActionsWithItemEntity getMysqlRepositoryActionByClassType(Class itemClassType) {
        return mysqlItemRepositoryActionMap.getOrDefault(itemClassType, null);
    }

    public List<ActionsWithItemEntity> getMysqlRepositoryActionList() {
        return new ArrayList<>(mysqlItemRepositoryActionMap.values());
    }
}
