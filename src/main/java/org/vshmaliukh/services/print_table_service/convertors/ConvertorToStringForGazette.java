package org.vshmaliukh.services.print_table_service.convertors;

import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.util.ArrayList;
import java.util.List;

public class ConvertorToStringForGazette implements ConvertorToString<Gazette> {

    @Override
    public List<String> convertObjectToListOfString(Gazette gazette) {
        List<String> list = new ArrayList<>();
        list.add(gazette.getClass().getSimpleName());
        list.add(gazette.getName());
        list.add(strValueOf(gazette.getPagesNumber()));
        list.add(strValueOf(gazette.isBorrowed()));
        return list;
    }

}
