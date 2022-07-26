package org.vshmaliukh.services.print_table_service.convertors;

import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.services.print_table_service.convertors.Titles.*;
import static org.vshmaliukh.services.print_table_service.convertors.Titles.BORROWED;

public class ConvertorToStringForMagazine implements ConvertorToString<Magazine> {

    @Override
    public Map<String, String> convertObjectToListOfString(Magazine magazine) {
        Map<String, String> map = new HashMap<>();
        map.put(TYPE, magazine.getClass().getSimpleName());
        map.put(NAME, magazine.getName());
        map.put(PAGES, strValueOf(magazine.getPagesNumber()));
        map.put(BORROWED, strValueOf(magazine.isBorrowed()));
        return new HashMap<>(map);
    }
}