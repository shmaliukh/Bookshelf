package org.vshmaliukh.services.print_table_service;

import java.util.List;
public interface ConvertorToString<T extends Object> {

    List<String> convertObjectToListOfString(T obj);

    default String strValueOf(Object o) {
        return String.valueOf(o);
    }
}
