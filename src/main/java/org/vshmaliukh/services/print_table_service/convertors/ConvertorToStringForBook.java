package org.vshmaliukh.services.print_table_service.convertors;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.util.HashMap;
import java.util.Map;

import static org.vshmaliukh.Terminal.DATE_FORMAT;
import static org.vshmaliukh.services.print_table_service.convertors.Titles.*;

public class ConvertorToStringForBook implements ConvertorToString<Book> {

    @Override
    public Map<String, String> convertObjectToListOfString(Book book) {
        Map<String, String> map = new HashMap<>();
        map.put(TYPE, book.getClass().getSimpleName());
        map.put(NAME, book.getName());
        map.put(PAGES, strValueOf(book.getPagesNumber()));
        map.put(BORROWED, strValueOf(book.isBorrowed()));
        map.put(AUTHOR, book.getAuthor());
        map.put(DATE, DATE_FORMAT.format(book.getIssuanceDate()));
        return new HashMap<>(map);
    }
}
