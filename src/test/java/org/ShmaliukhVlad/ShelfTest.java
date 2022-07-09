package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {
    PrintWriter printWriter = new PrintWriter(System.out);

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 24 * 1000));
    Book book2 = new Book("noNameBook2", 2, false, "NoAuthor2", new Date());
    Book book3 = new Book("noNameBook3", 3, true, "NoAuthor3", new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine magazine2 = new Magazine("noNameMagazine2", 2, false);
    Magazine magazine3 = new Magazine("noNameMagazine3", 3, true);


    @Test
    @DisplayName("Add one Book and one Magazine (are not borrowed) to empty Shelf")
    void addBookAndMagazineToShelf_1() {
        Shelf shelf1 = new Shelf(printWriter);

        Magazine magazine1 = new Magazine("4", 4, false);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().get(1) instanceof Magazine);
    }

    @Test
    @DisplayName("Add one Book (borrowed) and one Magazine (not borrowed) to empty Shelf")
    void addBookAndMagazineToShelf_2() {
        Shelf shelf1 = new Shelf(printWriter);
        Book book1 = new Book("1", 1, true, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().get(0) instanceof Magazine);
    }

    @Test
    @DisplayName("Generating Shelf with one Book and one Magazine")
    void generateShelf() {
        Shelf shelfTest = new Shelf(printWriter);
        Shelf shelfExpect = new Shelf(printWriter);
        Book book1 = new Book("1", 1, false, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
        shelfTest.addLiteratureObject(book1);
        shelfExpect.addLiteratureObject(magazine1);

        assertEquals(shelfTest.getLiteratureInShelf().size(), shelfExpect.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("Get sorted only Magazine objects by name")
    void sortedMagazinesByName() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazine3);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine1);
        shelf.printSortedMagazinesByName();

        ArrayList<Magazine> sortedMagazinesByName = shelf.getSortedMagazinesByName();

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        assertEquals(expectedLastName, sortedMagazinesByName.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedMagazinesByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Magazine objects by pages")
    void sortedMagazinesByPages() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazine3);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine1);

        ArrayList<Magazine> sortedMagazinesByPages = shelf.getSortedMagazinesByPages();

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        assertEquals(expectedLastName, sortedMagazinesByPages.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedMagazinesByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by name")
    void sortedBooksByName() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        ArrayList<Book> sortedBooksByName = shelf.getSortedBooksByName();

        assertEquals(expectedArraySize, sortedBooksByName.size());
        assertEquals(expectedLastName, sortedBooksByName.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by pages")
    void sortedBooksByPages() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        ArrayList<Book> sortedBooksByPages = shelf.getSortedBooksByPages();

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
        int expectedArraySize = 2;

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        ArrayList<Book> sortedBooksByAuthor = shelf.getSortedBooksByAuthor();

        assertEquals(expectedArraySize, sortedBooksByAuthor.size());
        assertEquals(expectedLastName, sortedBooksByAuthor.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByAuthor.get(1).getPagesNumber());
        assertEquals(expectedLastAuthor, sortedBooksByAuthor.get(1).getAuthor());
    }
}