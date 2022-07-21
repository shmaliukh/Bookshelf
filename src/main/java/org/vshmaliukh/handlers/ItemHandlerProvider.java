package org.vshmaliukh.handlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemHandlerProvider {
    private static Map<Class<? extends Item>, ItemHandler> itemHandlerMap = new ConcurrentHashMap<>();
    private static Map<String, Class> itemNameClassMap = new ConcurrentHashMap<>();

    static {
        itemHandlerMap.put(Book.class, new MagazineHandler());
        itemHandlerMap.put(Magazine.class, new MagazineHandler());
        itemHandlerMap.put(Gazette.class, new MagazineHandler());
        itemHandlerMap.forEach((k, v) -> itemNameClassMap.put(k.getSimpleName(), k));
    }

    public static ItemHandler getHandlerByClass(Class clazz) {
        return itemHandlerMap.get(clazz);
    }

    public static ItemHandler getHandlerByName(String name) {
        return getHandlerByClass(getClassByName(name));
    }

    public static Class getClassByName(String typeOfClass) {
        return itemNameClassMap.get(typeOfClass);
    }

    void initProvider() {

    }

    //TODO remove
    public static MagazineHandler getMagazineHandler() {
        return new MagazineHandler();
    }

}
