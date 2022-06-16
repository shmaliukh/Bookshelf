package org.ShmaliukhVlad.bookshelf.actionsWithShelf;

import jdk.jfr.Description;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class BaseActionsWithShelfTest {
    /**
     * Literature objects for tests
     */
    Book bookIsBorrowed = new Book("noNameBook1",1,true,"NoAuthor1", LocalDate.now());
    Book bookNotBorrowed = new Book("noNameBook2",2,false,"NoAuthor2",LocalDate.now().plus(Period.ofDays(1)));

    Book expectedBorrowedBook = new Book("noNameBook1",1,true,"NoAuthor1", LocalDate.now());
    Book expectedNotBorrowedBook = new Book("noNameBook2",2,false,"NoAuthor2",LocalDate.now().plus(Period.ofDays(1)));

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
    @Description("Simple test to add one magazine to shelf which is NOT borrowed")
    void addLiteratureObject_magazineNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazineNotBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedMagazine.getPagesNumber(), shelf.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedMagazine.getName(), shelf.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed magazine to the shelf")
    @Description("Simple test to add one magazine to shelf which is borrowed")
    void addLiteratureObject_magazineIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazineIsBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedBorrowedMagazine.getPagesNumber(), shelf.getLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedMagazine.getName(), shelf.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one not borrowed book to the shelf")
    @Description("Simple test to add one book to shelf which is NOT borrowed")
    void addLiteratureObject_bookNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookNotBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedBook.getPagesNumber(), shelf.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedBook.getName(), shelf.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed book to the shelf")
    @Description("Simple test to add one book to shelf which is borrowed")
    void addLiteratureObject_bookIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookIsBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedBorrowedBook.getPagesNumber(), shelf.getLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedBook.getName(), shelf.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to delete book from the shelf")
    @Description("Simple test to delete one book from shelf")
    void deleteLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookNotBorrowed);

        shelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete magazine from the shelf)")
    @Description("Simple test to delete one magazine from shelf")
    void deleteLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazineNotBorrowed);

        shelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete literature object from the shelf when empty")
    @Description("Check the console's response when trying to remove from an empty shelf")
    void deleteLiteratureObjectByIndex_emptyShelf() {
        String expectedString = "Empty shelf";

        System.setOut(ps);

        Shelf shelf = new Shelf();
        shelf.deleteLiteratureObjectByIndex(1);

        System.out.flush();
        System.setOut(old);

        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to delete literature object by wrong index from the shelf")
    @Description("Verify that the console responds to an attempt to remove an incorrect index from the shelf")
    void deleteLiteratureObjectByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        shelf.deleteLiteratureObjectByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(shelf.getLiteratureInShelf().isEmpty());
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow magazine from the shelf")
    @Description("Simple test to borrow one magazine from shelf")
    void borrowLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazineNotBorrowed);

        shelf.borrowLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow book from the shelf")
    @Description("Simple test to borrow one book from shelf")
    void borrowLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookNotBorrowed);

        shelf.borrowLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow no available literature object from the shelf")
    @Description("Verify that the console responds to an attempt to borrow literature from the shelf when no available literature object")
    void borrowLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "No available literature";
        Shelf shelf = new Shelf();

        System.setOut(ps);

        shelf.borrowLiteratureObjectFromShelfByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertTrue(shelf.getLiteratureInShelf().isEmpty());
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow literature object by wrong index from the shelf")
    @Description("Verify that the console responds to an attempt to borrow an incorrect index from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        shelf.borrowLiteratureObjectFromShelfByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(shelf.getLiteratureInShelf().isEmpty());
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive book back to the shelf")
    @Description("Simple test to arrive one book back to the shelf")
    void arriveLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookIsBorrowed);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive magazine back to the shelf")
    @Description("Simple test to arrive one magazine back to the shelf")
    void arriveLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazineIsBorrowed);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive literature object by wrong index back to the shelf")
    @Description("Verify that the console responds to an attempt to arrive an incorrect index back to the shelf")
    void arriveLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(bookIsBorrowed);

        System.setOut(ps);

        shelf.arriveLiteratureObjectFromShelfByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(shelf.getLiteratureOutShelf().isEmpty());
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive literature object back to the shelf when literature is not borrowed")
    @Description("Verify that the console responds to an attempt to arrive back to the shelf when literature is not borrowed")
    void arriveLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "Literature is not borrowed";
        Shelf shelf = new Shelf();

        System.setOut(ps);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        System.out.flush();
        System.setOut(old);

        assertTrue(shelf.getLiteratureOutShelf().isEmpty());
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void saveShelfToFile(){
        Shelf shelf = new Shelf();

        shelf.addLiteratureObject(bookNotBorrowed);
        shelf.addLiteratureObject(bookIsBorrowed);
        shelf.addLiteratureObject(magazineNotBorrowed);
        shelf.addLiteratureObject(magazineIsBorrowed);

        //TODO
    }
}