package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

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
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazineNotBorrowed);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedMagazine.getPagesNumber(), consoleShelfHandler.readLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedMagazine.getName(), consoleShelfHandler.readLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed magazine to the shelf")
    void addLiteratureObject_magazineIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazineIsBorrowed);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
        assertEquals(expectedBorrowedMagazine.getPagesNumber(), consoleShelfHandler.readLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedMagazine.getName(), consoleShelfHandler.readLiteratureOutShelf().get(0).getName());
    }


    @Test
    @DisplayName("test to add one not borrowed book to the shelf")
    void addLiteratureObject_bookNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookNotBorrowed);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedBook.getPagesNumber(), consoleShelfHandler.readLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedBook.getName(), consoleShelfHandler.readLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed book to the shelf")
    void addLiteratureObject_bookIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookIsBorrowed);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
        assertEquals(expectedBorrowedBook.getPagesNumber(), consoleShelfHandler.readLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedBook.getName(), consoleShelfHandler.readLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to delete book from the shelf")
    void deleteLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookNotBorrowed);

        consoleShelfHandler.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete magazine from the shelf)")
    void deleteLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazineNotBorrowed);

        consoleShelfHandler.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
    }


    @Test
    @DisplayName("test to delete literature object from the shelf when empty")
    void deleteLiteratureObjectByIndex_emptyShelf() {
        String expectedString = "Empty shelf";

        System.setOut(ps);

        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.deleteLiteratureObjectByIndex(1);

        System.out.flush();
        System.setOut(old);

        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to delete literature object by wrong index from the shelf")
    void deleteLiteratureObjectByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        consoleShelfHandler.deleteLiteratureObjectByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(consoleShelfHandler.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow magazine from the shelf")
    void borrowLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazineNotBorrowed);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureInShelf(), 1);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow book from the shelf")
    void borrowLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookNotBorrowed);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureInShelf(), 1);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow no available literature object from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "No available literature";
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);

        System.setOut(ps);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureInShelf(), 0);

        System.out.flush();
        System.setOut(old);

        assertTrue(consoleShelfHandler.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow literature object by wrong index from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureInShelf(), 0);

        System.out.flush();
        System.setOut(old);

        assertFalse(consoleShelfHandler.readLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive book back to the shelf")
    void arriveLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookIsBorrowed);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureOutShelf(), 1);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive magazine back to the shelf")
    void arriveLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(magazineIsBorrowed);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureOutShelf(),  1);

        assertEquals(expectedInShelfSize, consoleShelfHandler.readLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, consoleShelfHandler.readLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive literature object by wrong index back to the shelf")
    void arriveLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);
        consoleShelfHandler.addLiteratureObject(bookIsBorrowed);

        System.setOut(ps);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureOutShelf(),  0);

        System.out.flush();
        System.setOut(old);

        assertFalse(consoleShelfHandler.readLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive literature object back to the shelf when literature is not borrowed")
    void arriveLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "Literature is not borrowed";
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);

        System.setOut(ps);

        consoleShelfHandler.changeBorrowedStateOfItem(consoleShelfHandler.readLiteratureOutShelf(),  1);

        System.out.flush();
        System.setOut(old);

        assertTrue(consoleShelfHandler.readLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void saveShelfToFile(){
        ConsoleShelfHandler consoleShelfHandler = new ConsoleShelfHandler(new Scanner(""), printWriter);

        consoleShelfHandler.addLiteratureObject(bookNotBorrowed);
        consoleShelfHandler.addLiteratureObject(bookIsBorrowed);
        consoleShelfHandler.addLiteratureObject(magazineNotBorrowed);
        consoleShelfHandler.addLiteratureObject(magazineIsBorrowed);

        //TODO
    }
}