package org.vshmaliukh.terminal.bookshelf.literature_items;

import org.vshmaliukh.terminal.bookshelf.literature_items.book_item.Book;
import org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.Gazette;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.terminal.bookshelf.literature_items.book_item.BookHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.GazetteHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.MagazineHandler;

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
        itemHandlerMap.put(Gazette.class, new GazetteHandler());
        itemHandlerMap.put(Magazine.class, new MagazineHandler());
        itemHandlerMap.put(Book.class, new BookHandler());

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
