package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.convertors.BookEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemEntityConvertorsProvider {

    private ItemEntityConvertorsProvider() {
    }

    //    private static final Map<Class<? extends Item>, Class> itemClassEntityMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Item>, ItemEntityConvertor<? extends Item, ?>> itemEntityConvertorMap = new ConcurrentHashMap<>();


    static {
        itemEntityConvertorMap.put(Book.class, new BookEntityConvertor());
//        itemClassEntityMap.put(Book.class, BookEntity.class);
    }


//    public static Class getEntityClassByItemClass(Class<? extends Item> itemClassType) {
//        return itemClassEntityMap.get(itemClassType);
//    }

    public static ItemEntityConvertor<? extends Item, ?> getConvertorByItemClass(Class<? extends Item> itemClassType) {
        return itemEntityConvertorMap.getOrDefault(itemClassType, null);
    }
}
