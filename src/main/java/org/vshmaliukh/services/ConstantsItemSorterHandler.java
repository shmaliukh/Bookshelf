package org.vshmaliukh.services;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.Comparator;

public final class ConstantsItemSorterHandler {

    private ConstantsItemSorterHandler() {
    }

    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_PAGES = Comparator.comparing(Magazine::getPagesNumber);
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());

    public static final Comparator<Gazette> GAZETTE_COMPARATOR_BY_PAGES = Comparator.comparing(Gazette::getPagesNumber);
    public static final Comparator<Gazette> GAZETTE_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());

    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(o -> o.getAuthor().toLowerCase());
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());
}
