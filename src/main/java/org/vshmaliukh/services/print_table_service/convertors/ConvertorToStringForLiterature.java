package org.vshmaliukh.services.print_table_service.convertors;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.handlers.ItemHandlerProvider;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertorToStringForLiterature<T extends Item> {

    public List<String> getConvertedLiterature(T item) {
        return ItemHandlerProvider
                .getHandlerByName(item.getClass().getSimpleName())
                .getConvertorToString()
                .convertObjectToListOfString(item);
    }

    public List<List<String>> getTable(List<T> literatureList) {
        return literatureList.stream()
                .map(this::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
