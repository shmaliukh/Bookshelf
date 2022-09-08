package org.vshmaliukh.console_terminal_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;

class BaseActionsWithConsoleShelfTest {
    PrintWriter printWriter = new PrintWriter(System.out, true);

    Book bookIsBorrowed = new Book("noNameBook1",1,true,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book bookNotBorrowed = new Book("noNameBook2",2,false,"NoAuthor2",new Date());

    Book expectedBorrowedBook = new Book("noNameBook1",1,true,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book expectedNotBorrowedBook = new Book("noNameBook2",2,false,"NoAuthor2",new Date());

    Magazine magazineIsBorrowed = new Magazine("noNameMagazine1",1,true);
    Magazine magazineNotBorrowed = new Magazine("noNameMagazine2",2,false);

    Magazine expectedBorrowedMagazine = new Magazine("noNameMagazine1",1,true);
    Magazine expectedNotBorrowedMagazine = new Magazine("noNameMagazine2",2,false);

    /**
     * Special capture variables for the capture console output
     */
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;

    @Test
    @DisplayName("test to add one not borrowed magazine to the shelf")
    void addLiteratureObject_magazineNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(magazineNotBorrowed);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
        Assertions.assertEquals(expectedNotBorrowedMagazine.getPagesNumber(), shelfHandler.readLiteratureInShelf().get(0).getPagesNumber());
        Assertions.assertEquals(expectedNotBorrowedMagazine.getName(), shelfHandler.readLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed magazine to the shelf")
    void addLiteratureObject_magazineIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(magazineIsBorrowed);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
        Assertions.assertEquals(expectedBorrowedMagazine.getPagesNumber(), shelfHandler.readLiteratureOutShelf().get(0).getPagesNumber());
        Assertions.assertEquals(expectedBorrowedMagazine.getName(), shelfHandler.readLiteratureOutShelf().get(0).getName());
    }


    @Test
    @DisplayName("test to add one not borrowed book to the shelf")
    void addLiteratureObject_bookNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookNotBorrowed);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
        Assertions.assertEquals(expectedNotBorrowedBook.getPagesNumber(), shelfHandler.readLiteratureInShelf().get(0).getPagesNumber());
        Assertions.assertEquals(expectedNotBorrowedBook.getName(), shelfHandler.readLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed book to the shelf")
    void addLiteratureObject_bookIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookIsBorrowed);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
        Assertions.assertEquals(expectedBorrowedBook.getPagesNumber(), shelfHandler.readLiteratureOutShelf().get(0).getPagesNumber());
        Assertions.assertEquals(expectedBorrowedBook.getName(), shelfHandler.readLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to delete book from the shelf")
    void deleteLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookNotBorrowed);

        shelfHandler.deleteItemByIndex(1);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete magazine from the shelf)")
    void deleteLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(magazineNotBorrowed);

        shelfHandler.deleteItemByIndex(1);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
    }


    @Test
    @DisplayName("test to delete literature object from the shelf when empty")
    void deleteLiteratureObjectByIndex_emptyShelf() {
        String expectedString = "Empty shelf";

        System.setOut(ps);

        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.deleteItemByIndex(1);

        System.out.flush();
        System.setOut(old);

        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to delete literature object by wrong index from the shelf")
    void deleteLiteratureObjectByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookNotBorrowed);

        System.setOut(ps);

        shelfHandler.deleteItemByIndex(0);

        System.out.flush();
        System.setOut(old);

        Assertions.assertFalse(shelfHandler.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow magazine from the shelf")
    void borrowLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(magazineNotBorrowed);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureInShelf(), 1);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow book from the shelf")
    void borrowLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookNotBorrowed);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureInShelf(), 1);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow no available literature object from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "No available literature";
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();

        System.setOut(ps);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureInShelf(), 0);

        System.out.flush();
        System.setOut(old);

        Assertions.assertTrue(shelfHandler.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow literature object by wrong index from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookNotBorrowed);

        System.setOut(ps);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureInShelf(), 0);

        System.out.flush();
        System.setOut(old);

        Assertions.assertFalse(shelfHandler.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive book back to the shelf")
    void arriveLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookIsBorrowed);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureOutShelf(), 1);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive magazine back to the shelf")
    void arriveLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(magazineIsBorrowed);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureOutShelf(),  1);

        Assertions.assertEquals(expectedInShelfSize, shelfHandler.readLiteratureInShelf().size());
        Assertions.assertEquals(expectedOutShelfSize, shelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive literature object by wrong index back to the shelf")
    void arriveLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();
        shelfHandler.addItem(bookIsBorrowed);

        System.setOut(ps);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureOutShelf(),  0);

        System.out.flush();
        System.setOut(old);

        Assertions.assertFalse(shelfHandler.readLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive literature object back to the shelf when literature is not borrowed")
    void arriveLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "Literature is not borrowed";
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();

        System.setOut(ps);

        shelfHandler.changeBorrowedStateOfItem(shelfHandler.readLiteratureOutShelf(),  1);

        System.out.flush();
        System.setOut(old);

        Assertions.assertTrue(shelfHandler.readLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void saveShelfToFile(){
        AbstractShelfHandler shelfHandler = new AbstractShelfHandler();

        shelfHandler.addItem(bookNotBorrowed);
        shelfHandler.addItem(bookIsBorrowed);
        shelfHandler.addItem(magazineNotBorrowed);
        shelfHandler.addItem(magazineIsBorrowed);

        //TODO
    }
}