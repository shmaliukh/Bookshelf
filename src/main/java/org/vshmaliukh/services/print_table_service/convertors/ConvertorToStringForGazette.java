package org.vshmaliukh.services.print_table_service.convertors;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.util.HashMap;

import java.util.Map;

import static org.vshmaliukh.services.print_table_service.convertors.Titles.*;

public class ConvertorToStringForGazette implements ConvertorToString<Gazette> {

    @Override
    public Map<String, String> convertObjectToListOfString(Gazette gazette) {
        Map<String, String> map = new HashMap<>();
        map.put(TYPE, gazette.getClass().getSimpleName());
        map.put(NAME, gazette.getName());
        map.put(PAGES, strValueOf(gazette.getPagesNumber()));
        map.put(BORROWED, strValueOf(gazette.isBorrowed()));
        return new HashMap<>(map);
    }
}