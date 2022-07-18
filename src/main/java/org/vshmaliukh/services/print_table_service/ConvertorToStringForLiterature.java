package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertorToStringForLiterature {

    ConvertorToStringForMagazine convertorToStringForMagazine = new ConvertorToStringForMagazine();
    ConvertorToStringForBook convertorToStringForBook = new ConvertorToStringForBook();

    public static final String MAGAZINE_CLASS_NAME = Magazine.class.getCanonicalName();
    public static final String BOOK_CLASS_NAME = Book.class.getCanonicalName();

    public List<String> getConvertedLiterature(Literature literature) {
        if (literature.getClass().getCanonicalName().equals(MAGAZINE_CLASS_NAME)) {
            return convertorToStringForMagazine.convertObjectToListOfString((Magazine) literature);
        } else if (literature.getClass().getCanonicalName().equals(BOOK_CLASS_NAME)) {
            return convertorToStringForBook.convertObjectToListOfString((Book) literature);
        }
        return null; //FIXME fix cast + catch NULL
    }

    public List<List<String>> getTable(List<? extends Literature> literature){
        return literature.stream()
                .map(this::getConvertedLiterature)
                .collect(Collectors.toList());
    }
}
