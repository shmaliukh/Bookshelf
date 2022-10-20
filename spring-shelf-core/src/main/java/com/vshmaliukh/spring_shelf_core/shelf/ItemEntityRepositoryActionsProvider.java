package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.entities.*;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.ActionsWithItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class ItemEntityRepositoryActionsProvider {

    protected Map<Class, ActionsWithItemEntity> mysqlItemRepositoryActionMap;

    protected ActionsWithItemEntity<BookEntity> bookRepositoryActions;
    protected ActionsWithItemEntity<MagazineEntity> magazineRepositoryActions;
    protected ActionsWithItemEntity<NewspaperEntity> newspaperRepositoryActions;
    protected ActionsWithItemEntity<ComicsEntity> comicsRepositoryActions;

    @PostConstruct
    protected void initClassTypeRepositoryActionMap() {
        mysqlItemRepositoryActionMap = new ConcurrentHashMap<>();
        mysqlItemRepositoryActionMap.put(Book.class, bookRepositoryActions);
        mysqlItemRepositoryActionMap.put(Magazine.class, magazineRepositoryActions);
        mysqlItemRepositoryActionMap.put(Comics.class, comicsRepositoryActions);
        mysqlItemRepositoryActionMap.put(Newspaper.class, newspaperRepositoryActions);
    }

    public ActionsWithItemEntity<? extends ItemEntity> getRepositoryActionByClassType(Class itemClassType) {
        ActionsWithItemEntity repositoryAction = mysqlItemRepositoryActionMap.getOrDefault(itemClassType, null);
        if (repositoryAction == null) {
            log.error("[ItemEntityRepositoryActionsProvider] err: " +
                    "problem to get repositoryAction by '{}' item class type", itemClassType);
        }
        return repositoryAction;
    }

    public List<ActionsWithItemEntity> getRepositoryActionList() {
        return new ArrayList<>(mysqlItemRepositoryActionMap.values());
    }
}
