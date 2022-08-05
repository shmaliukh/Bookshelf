package org.vshmaliukh.terminal.services.print_table_service;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertorToStringForLiterature {

    public static <T extends Item> Map<String, String> getConvertedLiterature(T item) {
        return ItemHandlerProvider
                .getHandlerByName(item.getClass().getSimpleName())
                .convertItemToListOfString(item);
    }

    public static <T extends Item> List<Map<String, String>> getTable(List<T> literatureList) {
        return literatureList.stream()
                .map(ConvertorToStringForLiterature::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
