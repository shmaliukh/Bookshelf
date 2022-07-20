package org.vshmaliukh.services.print_table_service;

import java.util.List;
import java.util.stream.Collectors;

public interface ConvertorToString<T extends Object> {

    List<String> convertObjectToListOfString(T obj);

    default String strValueOf(Object o) {
        return String.valueOf(o);
    }

    default List<List<String>> getConvertedToStringList(List<T> list) {
        return list.stream()
                .map(this::convertObjectToListOfString)
                .collect(Collectors.toList());
    }
}
