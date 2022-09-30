package org.vshmaliukh.services;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ConvertorToStringForItems {

    private ConvertorToStringForItems(){}

    public static <T extends Item> Map<String, String> getConvertedLiterature(T item) {
        return ItemHandlerProvider
                .getHandlerByName(item.getClass().getSimpleName())
                .convertItemToMapOfString(item);
    }

    public static <T extends Item> List<Map<String, String>> getTable(List<T> literatureList) {
        return literatureList.stream()
                .map(ConvertorToStringForItems::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
