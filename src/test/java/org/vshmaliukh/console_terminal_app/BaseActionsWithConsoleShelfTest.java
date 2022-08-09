package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(magazineNotBorrowed);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedMagazine.getPagesNumber(), consoleShelf.readLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedMagazine.getName(), consoleShelf.readLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed magazine to the shelf")
    void addLiteratureObject_magazineIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(magazineIsBorrowed);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
        assertEquals(expectedBorrowedMagazine.getPagesNumber(), consoleShelf.readLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedMagazine.getName(), consoleShelf.readLiteratureOutShelf().get(0).getName());
    }


    @Test
    @DisplayName("test to add one not borrowed book to the shelf")
    void addLiteratureObject_bookNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookNotBorrowed);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedBook.getPagesNumber(), consoleShelf.readLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedBook.getName(), consoleShelf.readLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed book to the shelf")
    void addLiteratureObject_bookIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookIsBorrowed);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
        assertEquals(expectedBorrowedBook.getPagesNumber(), consoleShelf.readLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedBook.getName(), consoleShelf.readLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to delete book from the shelf")
    void deleteLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookNotBorrowed);

        consoleShelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete magazine from the shelf)")
    void deleteLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(magazineNotBorrowed);

        consoleShelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
    }


    @Test
    @DisplayName("test to delete literature object from the shelf when empty")
    void deleteLiteratureObjectByIndex_emptyShelf() {
        String expectedString = "Empty shelf";

        System.setOut(ps);

        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.deleteLiteratureObjectByIndex(1);

        System.out.flush();
        System.setOut(old);

        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to delete literature object by wrong index from the shelf")
    void deleteLiteratureObjectByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        consoleShelf.deleteLiteratureObjectByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(consoleShelf.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow magazine from the shelf")
    void borrowLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(magazineNotBorrowed);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureInShelf(), 1);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow book from the shelf")
    void borrowLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookNotBorrowed);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureInShelf(), 1);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow no available literature object from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "No available literature";
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);

        System.setOut(ps);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureInShelf(), 0);

        System.out.flush();
        System.setOut(old);

        assertTrue(consoleShelf.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow literature object by wrong index from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureInShelf(), 0);

        System.out.flush();
        System.setOut(old);

        assertFalse(consoleShelf.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive book back to the shelf")
    void arriveLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookIsBorrowed);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureOutShelf(), 1);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive magazine back to the shelf")
    void arriveLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(magazineIsBorrowed);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureOutShelf(),  1);

        assertEquals(expectedInShelfSize, consoleShelf.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelf.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive literature object by wrong index back to the shelf")
    void arriveLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);
        consoleShelf.addLiteratureObject(bookIsBorrowed);

        System.setOut(ps);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureOutShelf(),  0);

        System.out.flush();
        System.setOut(old);

        assertFalse(consoleShelf.readLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive literature object back to the shelf when literature is not borrowed")
    void arriveLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "Literature is not borrowed";
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);

        System.setOut(ps);

        consoleShelf.changeBorrowedStateOfItem(consoleShelf.readLiteratureOutShelf(),  1);

        System.out.flush();
        System.setOut(old);

        assertTrue(consoleShelf.readLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void saveShelfToFile(){
        ConsoleShelf consoleShelf = new ConsoleShelf(printWriter);

        consoleShelf.addLiteratureObject(bookNotBorrowed);
        consoleShelf.addLiteratureObject(bookIsBorrowed);
        consoleShelf.addLiteratureObject(magazineNotBorrowed);
        consoleShelf.addLiteratureObject(magazineIsBorrowed);

        //TODO
    }
}