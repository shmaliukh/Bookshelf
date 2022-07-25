package org.vshmaliukh.handlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.handlers.ItemHandlers.BookHandler;
import org.vshmaliukh.handlers.ItemHandlers.GazetteHandler;
import org.vshmaliukh.handlers.ItemHandlers.MagazineHandler;

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

    //TODO remove
    public static MagazineHandler getMagazineHandler() {
        return new MagazineHandler();
    }

    public static GazetteHandler getGazetteHandler() {
        return new GazetteHandler();
    }

    public static BookHandler getBookHandler() {
        return new BookHandler();
    }

}