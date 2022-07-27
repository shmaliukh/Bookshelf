package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.handlers.ItemHandlerProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertorToStringForLiterature<T extends Item> {

    public Map<String, String> getConvertedLiterature(T item) {
        return ItemHandlerProvider
                .getHandlerByName(item.getClass().getSimpleName())
                .convertItemToListOfString(item);
    }

    public List<Map<String, String>> getTable(List<T> literatureList) {
        return literatureList.stream()
                .map(this::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
