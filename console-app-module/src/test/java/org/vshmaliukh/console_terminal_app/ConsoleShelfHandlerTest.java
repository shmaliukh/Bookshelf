package org.vshmaliukh.console_terminal_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.shelf.literature_items.ItemUtils;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.book_item.BookHandler;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.magazine_item.MagazineHandler;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShelfHandlerTest {

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
        AbstractShelfHandler consoleShelf1 = new AbstractShelfHandler();

        Magazine magazine1 = new Magazine("4", 4, false);
        consoleShelf1.addItem(book1);
        consoleShelf1.addItem(magazine1);

        assertTrue(consoleShelf1.readLiteratureInShelf().get(0) instanceof Book
                && consoleShelf1.readLiteratureInShelf().get(1) instanceof Magazine);
    }

    @Test
    @DisplayName("Add one Book (borrowed), one Magazine and Gazette (not borrowed) to empty Shelf")
    void addItemsToShelf_2() {
        AbstractShelfHandler consoleShelf1  = new AbstractShelfHandler();
        Book book1 = new Book("1", 1, true, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
        consoleShelf1.addItem(book1);
        consoleShelf1.addItem(magazine1);

        assertTrue(consoleShelf1.readLiteratureOutShelf().get(0) instanceof Book
                && consoleShelf1.readLiteratureInShelf().get(0) instanceof Magazine);

    }

    @Test
    @DisplayName("Get sorted only Magazine objects by name")
    void sortedMagazinesByName() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        AbstractShelfHandler shelfHandler  = new AbstractShelfHandler();
        shelfHandler.addItem(magazine3);
        shelfHandler.addItem(magazine2);
        shelfHandler.addItem(magazine1);

        List<Magazine> sortedMagazinesByName =
                ItemUtils.getSortedLiterature(
                        ItemUtils.getItemsByType(Magazine.class, shelfHandler.getShelf().getAllLiteratureObjects()),
                        MagazineHandler.MAGAZINE_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        Assertions.assertEquals(expectedLastName, sortedMagazinesByName.get(1).getName());
        Assertions.assertEquals(expectedLastPagesNumber, sortedMagazinesByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Magazine objects by pages")
    void sortedMagazinesByPages() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        AbstractShelfHandler shelfHandler  = new AbstractShelfHandler();
        shelfHandler.addItem(magazine3);
        shelfHandler.addItem(magazine2);
        shelfHandler.addItem(magazine1);

        List<Magazine> sortedMagazinesByPages =
                ItemUtils.getSortedLiterature(
                        ItemUtils.getItemsByType(Magazine.class, shelfHandler.getShelf().getAllLiteratureObjects()),
                        MagazineHandler.MAGAZINE_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        Assertions.assertEquals(expectedLastName, sortedMagazinesByPages.get(1).getName());
        Assertions.assertEquals(expectedLastPagesNumber, sortedMagazinesByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by name")
    void sortedBooksByName() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        AbstractShelfHandler shelfHandler  = new AbstractShelfHandler();
        shelfHandler.addItem(book3);
        shelfHandler.addItem(book2);
        shelfHandler.addItem(book1);

        List<Book> sortedBooksByName =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, shelfHandler.getShelf().getAllLiteratureObjects()),
                        BookHandler.BOOK_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedBooksByName.size());
        Assertions.assertEquals(expectedLastName, sortedBooksByName.get(1).getName());
        Assertions.assertEquals(expectedLastPagesNumber, sortedBooksByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by pages")
    void sortedBooksByPages() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        AbstractShelfHandler shelfHandler  = new AbstractShelfHandler();
        shelfHandler.addItem(book3);
        shelfHandler.addItem(book2);
        shelfHandler.addItem(book1);

        List<Book> sortedBooksByPages =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, shelfHandler.getShelf().getAllLiteratureObjects()),
                        BookHandler.BOOK_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedBooksByPages.size());
        Assertions.assertEquals(expectedLastName, sortedBooksByPages.get(1).getName());
        Assertions.assertEquals(expectedLastPagesNumber, sortedBooksByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by author")
    void sortedBooksByAuthor() {
        String expectedLastName = "noNameBook2";
        String expectedLastAuthor = "NoAuthor2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 3;

        AbstractShelfHandler shelfHandler  = new AbstractShelfHandler();
        shelfHandler.addItem(book3);
        shelfHandler.addItem(book2);
        shelfHandler.addItem(book1);

        List<Book> sortedBooksByAuthor =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Book.class, shelfHandler.getShelf().getAllLiteratureObjects()),
                        BookHandler.BOOK_COMPARATOR_BY_AUTHOR);

        assertEquals(expectedArraySize, sortedBooksByAuthor.size());
        Assertions.assertEquals(expectedLastName, sortedBooksByAuthor.get(1).getName());
        Assertions.assertEquals(expectedLastPagesNumber, sortedBooksByAuthor.get(1).getPagesNumber());
        Assertions.assertEquals(expectedLastAuthor, sortedBooksByAuthor.get(1).getAuthor());
    }
}