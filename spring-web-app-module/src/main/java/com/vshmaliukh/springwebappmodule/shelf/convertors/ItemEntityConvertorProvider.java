package com.vshmaliukh.springwebappmodule.shelf.convertors;

import com.vshmaliukh.springwebappmodule.shelf.convertors.imp.*;
import com.vshmaliukh.springwebappmodule.shelf.entities.*;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemEntityConvertorProvider {

    private ItemEntityConvertorProvider(){}

    private static final Map<Class<? extends Item>, ItemEntityConvertor<? extends Item, ? extends ItemEntity>> itemClassTypeConvertorMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends ItemEntity>, ItemEntityConvertor<? extends Item, ? extends ItemEntity>> entityClassTypeConvertorMap = new ConcurrentHashMap<>();

    static {// todo refactor
        entityClassTypeConvertorMap.put(SqliteBookEntity.class, new SqliteBookEntityConvertor());

        entityClassTypeConvertorMap.put(BookEntity.class, new BookEntityConvertor());
        entityClassTypeConvertorMap.put(MagazineEntity.class, new MagazineEntityConvertor());
        entityClassTypeConvertorMap.put(NewspaperEntity.class, new NewspaperEntityConvertor());
        entityClassTypeConvertorMap.put(ComicsEntity.class, new ComicsEntityConvertor());

        itemClassTypeConvertorMap.put(Book.class, new BookEntityConvertor());
        itemClassTypeConvertorMap.put(Magazine.class, new MagazineEntityConvertor());
        itemClassTypeConvertorMap.put(Newspaper.class, new NewspaperEntityConvertor());
        itemClassTypeConvertorMap.put(Comics.class, new ComicsEntityConvertor());
    }

    public static <I extends Item> ItemEntityConvertor getConvertorByItemClassType(Class<I> itemClassType){
        return itemClassTypeConvertorMap.getOrDefault(itemClassType, null);
    }

    public static <E extends ItemEntity> ItemEntityConvertor getConvertorByEntityClassType(Class<E> entityClassType){
        return entityClassTypeConvertorMap.getOrDefault(entityClassType, null);
    }

}
