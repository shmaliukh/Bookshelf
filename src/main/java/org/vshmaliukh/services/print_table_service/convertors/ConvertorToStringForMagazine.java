package org.vshmaliukh.services.print_table_service.convertors;

import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.util.Arrays;
import java.util.List;

public class ConvertorToStringForMagazine implements ConvertorToString<Magazine> {

    @Override
    public List<String> convertObjectToListOfString(Magazine magazine) {
        return Arrays.asList(
                magazine.getClass().getSimpleName(),
                magazine.getName(),
                strValueOf(magazine.getPagesNumber()),
                strValueOf(magazine.isBorrowed())
        );
    }
}