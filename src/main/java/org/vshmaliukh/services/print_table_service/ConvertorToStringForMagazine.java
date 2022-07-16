package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.ArrayList;
import java.util.List;

public class ConvertorToStringForMagazine implements ConvertorToString<Magazine> {

    @Override
    public List<String> convertObjectToListOfString(Magazine magazine) {
        List<String> list = new ArrayList<>();
        list.add(magazine.getClass().getSimpleName());
        list.add(magazine.getName());
        list.add(strValueOf(magazine.getPagesNumber()));
        list.add(strValueOf(magazine.isBorrowed()));
        return list;
    }
}
