package org.vshmaliukh.services.print_table_service;

import java.util.Map;

public interface ConvertorToString<T extends Object> {

    Map<String, String> convertObjectToListOfString(T obj);

    default String strValueOf(Object o) {
        return String.valueOf(o);
    }
}
