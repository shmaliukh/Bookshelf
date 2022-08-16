package org.vshmaliukh.console_terminal_app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.ItemUtils;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.shelf.literature_items.book_item.BookHandler.*;
import static org.vshmaliukh.shelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.shelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_PAGES;

class ConsoleShelfHandlerTest {

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
        ConsoleGsonShelfHandler consoleShelf1 = new ConsoleGsonShelfHandler(new Scanner(""),printWriter);

        Magazine magazine1 = new Magazine("4", 4, false);
        consoleShelf1.addLiteratureObject(book1);
        consoleShelf1.addLiteratureObject(magazine1);

        assertTrue(consoleShelf1.readLiteratureInShelf().get(0) instanceof Book
                && consoleShelf1.readLiteratureInShelf().get(1) instanceof Magazine);
    }

    @Test
    @DisplayName("Add one Book (borrowed), one Magazine and Gazette (not borrowed) to empty Shelf")
    void addItemsToShelf_2() {
        ConsoleGsonShelfHandler consoleShelf1 = new ConsoleGsonShelfHandler(new Scanner(""),printWriter);
        Book book1 = new Book("1", 1, true, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
        consoleShelf1.addLiteratureObject(book1);
        consoleShelf1.addLiteratureObject(magazine1);

        assertTrue(consoleShelf1.readLiteratureOutShelf().get(0) instanceof Book
                && consoleShelf1.readLiteratureInShelf().get(0) instanceof Magazine);

    }

    @Test
    @DisplayName("Get sorted only Magazine objects by name")
    void sortedMagazinesByName() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazine3);
        consoleShelfHandler.addLiteratureObject(magazine2);
        consoleShelfHandler.addLiteratureObject(magazine1);

        List<Magazine> sortedMagazinesByName =
                ItemUtils.getSortedLiterature(
                        ItemUtils.getItemsByType(Magazine.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
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

        ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazine3);
        consoleShelfHandler.addLiteratureObject(magazine2);
        consoleShelfHandler.addLiteratureObject(magazine1);

        List<Magazine> sortedMagazinesByPages =
                ItemUtils.getSortedLiterature(
                        ItemUtils.getItemsByType(Magazine.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
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

        ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(book3);
        consoleShelfHandler.addLiteratureObject(book2);
        consoleShelfHandler.addLiteratureObject(book1);

        List<Book> sortedBooksByName =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
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

        ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(book3);
        consoleShelfHandler.addLiteratureObject(book2);
        consoleShelfHandler.addLiteratureObject(book1);

        List<Book> sortedBooksByPages =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
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

        ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(book3);
        consoleShelfHandler.addLiteratureObject(book2);
        consoleShelfHandler.addLiteratureObject(book1);

        List<Book> sortedBooksByAuthor =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
                        BOOK_COMPARATOR_BY_AUTHOR);

        assertEquals(expectedArraySize, sortedBooksByAuthor.size());
        assertEquals(expectedLastName, sortedBooksByAuthor.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByAuthor.get(1).getPagesNumber());
        assertEquals(expectedLastAuthor, sortedBooksByAuthor.get(1).getAuthor());
    }
}