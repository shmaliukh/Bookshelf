package org.vshmaliukh.constants;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.Comparator;

public final class ConstantsLiteratureSorterHandler {

    private ConstantsLiteratureSorterHandler(){}

    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_PAGES = Comparator.comparing(Magazine::getPagesNumber);
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());

    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(o -> o.getIssuanceDate().getTime());
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(o -> o.getAuthor().toLowerCase());
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());
}
