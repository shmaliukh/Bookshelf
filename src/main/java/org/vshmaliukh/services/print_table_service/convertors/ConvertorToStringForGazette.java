package org.vshmaliukh.services.print_table_service.convertors;

import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.util.Arrays;
import java.util.List;

public class ConvertorToStringForGazette implements ConvertorToString<Gazette> {

    @Override
    public List<String> convertObjectToListOfString(Gazette gazette) {
        return Arrays.asList(
                gazette.getClass().getSimpleName(),
                gazette.getName(),
                strValueOf(gazette.getPagesNumber()),
                strValueOf(gazette.isBorrowed())
        );
    }
}