package com.vshmaliukh.spring_shelf_core.shelf.convertors;

import com.vshmaliukh.spring_shelf_core.shelf.convertors.imp.BookEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.convertors.imp.ComicsEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.convertors.imp.MagazineEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.convertors.imp.NewspaperEntityConvertor;
import com.vshmaliukh.spring_shelf_core.shelf.entities.*;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemEntityConvertorProvider {

    public static final ItemEntityConvertor<? extends Item, ? extends ItemEntity> DEFAULT_ITEM_ENTITY_CONVERTOR_VALUE = null;

    private ItemEntityConvertorProvider(){}

    private static final Map<Class<? extends Item>, ItemEntityConvertor<? extends Item, ? extends ItemEntity>> itemClassTypeConvertorMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends ItemEntity>, ItemEntityConvertor<? extends Item, ? extends ItemEntity>> entityClassTypeConvertorMap = new ConcurrentHashMap<>();

    static {
        entityClassTypeConvertorMap.put(BookEntity.class, new BookEntityConvertor());
        entityClassTypeConvertorMap.put(MagazineEntity.class, new MagazineEntityConvertor());
        entityClassTypeConvertorMap.put(NewspaperEntity.class, new NewspaperEntityConvertor());
        entityClassTypeConvertorMap.put(ComicsEntity.class, new ComicsEntityConvertor());
        MyLogUtil.logDebug(ItemEntityConvertorProvider.class, "entity class type - convertor map : '{}'", entityClassTypeConvertorMap);

        itemClassTypeConvertorMap.put(Book.class, new BookEntityConvertor());
        itemClassTypeConvertorMap.put(Magazine.class, new MagazineEntityConvertor());
        itemClassTypeConvertorMap.put(Newspaper.class, new NewspaperEntityConvertor());
        itemClassTypeConvertorMap.put(Comics.class, new ComicsEntityConvertor());
        MyLogUtil.logDebug(ItemEntityConvertorProvider.class, "item class type - convertor map : '{}'", itemClassTypeConvertorMap);
    }

    public static <I extends Item> ItemEntityConvertor getConvertorByItemClassType(Class<I> itemClassType){
        ItemEntityConvertor<? extends Item, ? extends ItemEntity> convertor = itemClassTypeConvertorMap.getOrDefault(itemClassType, DEFAULT_ITEM_ENTITY_CONVERTOR_VALUE);
        if(convertor == null){
            MyLogUtil.logWarn(ItemEntityConvertorProvider.class, "not found itemClassTypeConvertor // getConvertorByItemClassType(itemClassType: '{}') " +
                    "// return default value: '{}'", itemClassType, DEFAULT_ITEM_ENTITY_CONVERTOR_VALUE);
        }
        return convertor;
    }

    public static <E extends ItemEntity> ItemEntityConvertor getConvertorByEntityClassType(Class<E> entityClassType){
        ItemEntityConvertor<? extends Item, ? extends ItemEntity> convertor = entityClassTypeConvertorMap.getOrDefault(entityClassType, DEFAULT_ITEM_ENTITY_CONVERTOR_VALUE);
        if(convertor == null){
            MyLogUtil.logWarn(ItemEntityConvertorProvider.class, "not found itemClassTypeConvertor // getConvertorByEntityClassType(entityClassType: '{}') " +
                    "// return default value: '{}'", entityClassType, DEFAULT_ITEM_ENTITY_CONVERTOR_VALUE);
        }
        return convertor;
    }

}
