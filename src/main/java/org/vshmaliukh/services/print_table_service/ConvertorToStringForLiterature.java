package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertorToStringForLiterature <T extends Item> {

    public static final String MAGAZINE_CLASS_NAME = Magazine.class.getSimpleName();
    public static final String BOOK_CLASS_NAME = Book.class.getSimpleName();

    public List<String> getConvertedLiterature(T item) {
        if (item instanceof Magazine) {
            return new ConvertorToStringForMagazine().convertObjectToListOfString((Magazine) item);
        } else if (item instanceof Book) {
            return new ConvertorToStringForBook().convertObjectToListOfString((Book) item);
        }
        return Collections.emptyList(); //FIXME fix cast
    }

    public List<List<String>> getTable(List<T> literatureList){
        return literatureList.stream()
                .map(this::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
