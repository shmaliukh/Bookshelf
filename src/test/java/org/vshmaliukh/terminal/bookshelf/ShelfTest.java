package org.vshmaliukh.terminal.bookshelf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.terminal.bookshelf.literature_items.book_item.Book;

import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.terminal.services.Utils;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.terminal.bookshelf.literature_items.book_item.BookHandler.*;
import static org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_PAGES;

class ShelfTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 24 * 1000));
    Book book2 = new Book("noNameBook2", 2, false, "NoAuthor2", new Date());
    Book book3 = new Book("noNameBook3", 3, true, "NoAuthor3", new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine magazine2 = new Magazine("noNameMagazine2", 2, false);
    Magazine magazine3 = new Magazine("noNameMagazine3", 3, true);


    @Test
    @DisplayName("Add one Book, Magazine and Gazette (are not borrowed) to empty Shelf")
    void addItemsToShelf_1() {
        Shelf shelf1 = new Shelf(printWriter);

        Magazine magazine1 = new Magazine("4", 4, false);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().get(1) instanceof Magazine);
    }

    @Test
    @DisplayName("Add one Book (borrowed), one Magazine and Gazette (not borrowed) to empty Shelf")
    void addItemsToShelf_2() {
        Shelf shelf1 = new Shelf(printWriter);
        Book book1 = new Book("1", 1, true, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().get(0) instanceof Magazine);

    }

    @Test
    @DisplayName("Get sorted only Magazine objects by name")
    void sortedMagazinesByName() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazine3);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine1);

        List<Magazine> sortedMagazinesByName =
                Utils.getSortedLiterature(
                        Utils.getItemsByType(Magazine.class, shelf.getAllLiteratureObjects()),
                        MAGAZINE_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        assertEquals(expectedLastName, sortedMagazinesByName.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedMagazinesByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Magazine objects by pages")
    void sortedMagazinesByPages() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazine3);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine1);

        List<Magazine> sortedMagazinesByPages =
                Utils.getSortedLiterature(
                        Utils.getItemsByType(Magazine.class, shelf.getAllLiteratureObjects()),
                        MAGAZINE_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        assertEquals(expectedLastName, sortedMagazinesByPages.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedMagazinesByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by name")
    void sortedBooksByName() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        List<Book> sortedBooksByName =
                Utils.getSortedLiterature(Utils.getItemsByType(Book.class, shelf.getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedBooksByName.size());
        assertEquals(expectedLastName, sortedBooksByName.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by pages")
    void sortedBooksByPages() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        List<Book> sortedBooksByPages =
                Utils.getSortedLiterature(Utils.getItemsByType(Book.class, shelf.getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedBooksByPages.size());
        assertEquals(expectedLastName, sortedBooksByPages.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by author")
    void sortedBooksByAuthor() {
        String expectedLastName = "noNameBook2";
        String expectedLastAuthor = "NoAuthor2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        List<Book> sortedBooksByAuthor =
                Utils.getSortedLiterature(Utils.getItemsByType(Book.class, shelf.getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_AUTHOR);

        assertEquals(expectedArraySize, sortedBooksByAuthor.size());
        assertEquals(expectedLastName, sortedBooksByAuthor.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByAuthor.get(1).getPagesNumber());
        assertEquals(expectedLastAuthor, sortedBooksByAuthor.get(1).getAuthor());
    }
}