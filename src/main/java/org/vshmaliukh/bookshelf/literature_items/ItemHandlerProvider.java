package org.vshmaliukh.bookshelf.literature_items;

import org.vshmaliukh.bookshelf.literature_items.book_item.Book;
import org.vshmaliukh.bookshelf.literature_items.comics_item.Comics;
import org.vshmaliukh.bookshelf.literature_items.comics_item.ComicsHandler;
import org.vshmaliukh.bookshelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.bookshelf.literature_items.book_item.BookHandler;
import org.vshmaliukh.bookshelf.literature_items.magazine_item.MagazineHandler;
import org.vshmaliukh.bookshelf.literature_items.newspaper_item.Newspaper;
import org.vshmaliukh.bookshelf.literature_items.newspaper_item.NewspaperHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ItemHandlerProvider {
    private static final Map<Class<? extends Item>, ItemHandler> itemHandlerMap = new ConcurrentHashMap<>();
    private static final Map<String, Class> itemNameClassMap = new ConcurrentHashMap<>();
    public static final Set<Class<? extends Item>> uniqueTypeNames;

    static {
        itemHandlerMap.put(Magazine.class, new MagazineHandler());
        itemHandlerMap.put(Newspaper.class, new NewspaperHandler());
        itemHandlerMap.put(Book.class, new BookHandler());
        itemHandlerMap.put(Comics.class, new ComicsHandler());

        itemHandlerMap.forEach((k, v) -> itemNameClassMap.put(k.getSimpleName(), k));
        uniqueTypeNames = Collections.unmodifiableSet(new HashSet<>(itemHandlerMap.keySet()));
    }

    public static ItemHandler getHandlerByClass(Class clazz) {
        return itemHandlerMap.get(clazz);
    }

    public static ItemHandler getHandlerByName(String name) {
        return getHandlerByClass(getClassByName(name));
    }

    public static Class<? extends Item> getClassByName(String typeOfClass) {
        return itemNameClassMap.get(typeOfClass);
    }
}
