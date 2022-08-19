package org.vshmaliukh.shelf.literature_items.book_item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.shelf.literature_items.ItemUtils;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.shelf.literature_items.book_item.BookHandler.*;
import static org.vshmaliukh.shelf.literature_items.book_item.BookHandler.BOOK_COMPARATOR_BY_DATE;

class ActionsWithBookTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    Book book2 = new Book("noNameBook2", 2, false, "NoAuthor2", new Date(System.currentTimeMillis() - 60 * 60 * 64));
    Book book3 = new Book("noNameBook3", 3, true, "NoAuthor3", new Date());

    Book expectedBook1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    Book expectedBook2 = new Book("noNameBook2", 2, false, "NoAuthor2", new Date());

    /**
     * The book3 is not expected to be included for sorting
     */
    int expectedArraySize = 3;

    AbstractShelfHandler consoleShelfHandler = new AbstractShelfHandler();

    /*
      Adding books in revers order
     */ {
        consoleShelfHandler.addItem(book3);
        consoleShelfHandler.addItem(book2);
        consoleShelfHandler.addItem(book1);
    }


    @Test
    @DisplayName("test printable stings of sorted Books by Name")
    void printSortedBooksByName() {
        List<Book> sortedBooksByName =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getItemsOfShelf()),
                        BOOK_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedBooksByName.size());
        assertEquals(expectedBook1.toString(), sortedBooksByName.get(0).toString());
        assertEquals(expectedBook2.toString(), sortedBooksByName.get(1).toString());
    }

    @Test
    @DisplayName("test printable stings of sorted Books by Pages")
    void printSortedBooksByPages() {
        List<Book> sortedBooksByPages =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedBooksByPages.size());
        assertEquals(expectedBook1.toString(), sortedBooksByPages.get(0).toString());
        assertEquals(expectedBook2.toString(), sortedBooksByPages.get(1).toString());
    }

    @Test
    @DisplayName("test printable stings of sorted Books by Author")
    void printSortedBooksByAuthor() {
        List<Book> sortedBooksByAuthor =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_AUTHOR);

        assertEquals(expectedArraySize, sortedBooksByAuthor.size());
        assertEquals(expectedBook1.toString(), sortedBooksByAuthor.get(0).toString());
        assertEquals(expectedBook2.toString(), sortedBooksByAuthor.get(1).toString());
    }

    @Test
    @DisplayName("test printable stings of sorted Books by Date")
    void printSortedBooksByDate() {
        List<Book> sortedBooksByDate =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_DATE);

        assertEquals(expectedArraySize, sortedBooksByDate.size());
        assertEquals(expectedBook1.toString(), sortedBooksByDate.get(0).toString());
        assertEquals(expectedBook2.toString(), sortedBooksByDate.get(1).toString());
    }
}