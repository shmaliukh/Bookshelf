package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;

import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.constants.ConstantsForTerminal.DATE_FORMAT;

public class ConvertorToStringForBook implements ConvertorToString<Book> {

    @Override
    public List<String> convertObjectToListOfString(Book book) {
        List<String> list = new ArrayList<>();
        list.add(book.getClass().getSimpleName());
        list.add(book.getName());
        list.add(strValueOf(book.getPagesNumber()));
        list.add(strValueOf(book.isBorrowed()));
        list.add(book.getAuthor());
        list.add(DATE_FORMAT.format(book.getIssuanceDate()));
        return list;
    }
}
