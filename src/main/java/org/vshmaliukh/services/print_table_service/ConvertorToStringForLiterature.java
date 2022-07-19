package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertorToStringForLiterature {

    private final ConvertorToStringForMagazine convertorToStringForMagazine = new ConvertorToStringForMagazine();
    private final ConvertorToStringForBook convertorToStringForBook = new ConvertorToStringForBook();

    public static final String MAGAZINE_CLASS_NAME = Magazine.class.getSimpleName();
    public static final String BOOK_CLASS_NAME = Book.class.getSimpleName();

    public List<String> getConvertedLiterature(Item item) {
        if (item instanceof Magazine) {
            return convertorToStringForMagazine.convertObjectToListOfString((Magazine) item);
        } else if (item instanceof Book) {
            return convertorToStringForBook.convertObjectToListOfString((Book) item);
        }
        return Collections.emptyList(); //FIXME fix cast
    }

    public List<List<String>> getTable(List<? extends Item> literatures){
        return literatures.stream()
                .map(this::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
