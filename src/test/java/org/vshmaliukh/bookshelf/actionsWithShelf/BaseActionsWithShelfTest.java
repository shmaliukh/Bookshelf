package org.vshmaliukh.bookshelf.actionsWithShelf;

import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BaseActionsWithShelfTest {
    /**
     * Literature objects for tests
     */
    PrintWriter printWriter = new PrintWriter(System.out, true);
    
    Book bookIsBorrowed = new Book("noNameBook1",1,true,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book bookNotBorrowed = new Book("noNameBook2",2,false,"NoAuthor2",new Date());

    Book expectedBorrowedBook = new Book("noNameBook1",1,true,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book expectedNotBorrowedBook = new Book("noNameBook2",2,false,"NoAuthor2",new Date());

    Magazine magazineIsBorrowed = new Magazine("noNameMagazine1",1,true);
    Magazine magazineNotBorrowed = new Magazine("noNameMagazine2",2,false);

    Magazine expectedBorrowedMagazine = new Magazine("noNameMagazine1",1,true);
    Magazine expectedNotBorrowedMagazine = new Magazine("noNameMagazine2",2,false);

    Gazette gazetteIsBorrowed = new Gazette("noNameGazette1",1,true);
    Gazette gazetteNotBorrowed = new Gazette("noNameGazette2",2,false);

    Gazette expectedBorrowedGazette = new Gazette("noNameGazette1",1,true);
    Gazette expectedNotBorrowedGazette = new Gazette("noNameGazette2",2,false);

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
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazineNotBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedMagazine.getPagesNumber(), shelf.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedMagazine.getName(), shelf.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed magazine to the shelf")
    void addLiteratureObject_magazineIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazineIsBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedBorrowedMagazine.getPagesNumber(), shelf.getLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedMagazine.getName(), shelf.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one not borrowed gazette to the shelf")
    void addLiteratureObject_gazetteNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(gazetteNotBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedGazette.getPagesNumber(), shelf.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedGazette.getName(), shelf.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed gazette to the shelf")
    void addLiteratureObject_gazetteIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(gazetteIsBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedBorrowedGazette.getPagesNumber(), shelf.getLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedGazette.getName(), shelf.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one not borrowed book to the shelf")
    void addLiteratureObject_bookNotBorrowed() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookNotBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedNotBorrowedBook.getPagesNumber(), shelf.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(expectedNotBorrowedBook.getName(), shelf.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to add one borrowed book to the shelf")
    void addLiteratureObject_bookIsBorrowed() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookIsBorrowed);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
        assertEquals(expectedBorrowedBook.getPagesNumber(), shelf.getLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(expectedBorrowedBook.getName(), shelf.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("test to delete book from the shelf")
    void deleteLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookNotBorrowed);

        shelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete gazette from the shelf)")
    void deleteLiteratureObjectByIndex_gazette() {
        int expectedInShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(gazetteNotBorrowed);

        shelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("test to delete magazine from the shelf)")
    void deleteLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazineNotBorrowed);

        shelf.deleteLiteratureObjectByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
    }


    @Test
    @DisplayName("test to delete literature object from the shelf when empty")
    void deleteLiteratureObjectByIndex_emptyShelf() {
        String expectedString = "Empty shelf";

        System.setOut(ps);

        Shelf shelf = new Shelf(printWriter);
        shelf.deleteLiteratureObjectByIndex(1);

        System.out.flush();
        System.setOut(old);

        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to delete literature object by wrong index from the shelf")
    void deleteLiteratureObjectByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        shelf.deleteLiteratureObjectByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(shelf.getLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow magazine from the shelf")
    void borrowLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazineNotBorrowed);

        shelf.borrowLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow gazette from the shelf")
    void borrowLiteratureObjectByIndex_gazette() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(gazetteNotBorrowed);

        shelf.borrowLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow book from the shelf")
    void borrowLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 0;
        int expectedOutShelfSize = 1;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookNotBorrowed);

        shelf.borrowLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to borrow no available literature object from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "No available literature";
        Shelf shelf = new Shelf(printWriter);

        System.setOut(ps);

        shelf.borrowLiteratureObjectFromShelfByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertTrue(shelf.getLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to borrow literature object by wrong index from the shelf")
    void borrowLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookNotBorrowed);

        System.setOut(ps);

        shelf.borrowLiteratureObjectFromShelfByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(shelf.getLiteratureInShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive book back to the shelf")
    void arriveLiteratureObjectByIndex_book() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookIsBorrowed);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive gazette back to the shelf")
    void arriveLiteratureObjectByIndex_gazette() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(gazetteIsBorrowed);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive magazine back to the shelf")
    void arriveLiteratureObjectByIndex_magazine() {
        int expectedInShelfSize = 1;
        int expectedOutShelfSize = 0;
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(magazineIsBorrowed);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        assertEquals(expectedInShelfSize, shelf.getLiteratureInShelf().size());
        assertEquals(expectedOutShelfSize, shelf.getLiteratureOutShelf().size());
    }

    @Test
    @DisplayName("test to arrive literature object by wrong index back to the shelf")
    void arriveLiteratureObjectFromShelfByIndex_wrongIndex() {
        String expectedString = "Wrong index";
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(bookIsBorrowed);

        System.setOut(ps);

        shelf.arriveLiteratureObjectFromShelfByIndex(0);

        System.out.flush();
        System.setOut(old);

        assertFalse(shelf.getLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    @DisplayName("test to arrive literature object back to the shelf when literature is not borrowed")
    void arriveLiteratureObjectFromShelfByIndex_noBorrowed() {
        String expectedString = "Literature is not borrowed";
        Shelf shelf = new Shelf(printWriter);

        System.setOut(ps);

        shelf.arriveLiteratureObjectFromShelfByIndex(1);

        System.out.flush();
        System.setOut(old);

        assertTrue(shelf.getLiteratureOutShelf().isEmpty());
        //FIXME fix test
        //assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void saveShelfToFile(){
        Shelf shelf = new Shelf(printWriter);

        shelf.addLiteratureObject(bookNotBorrowed);
        shelf.addLiteratureObject(bookIsBorrowed);
        shelf.addLiteratureObject(magazineNotBorrowed);
        shelf.addLiteratureObject(magazineIsBorrowed);

        //TODO
    }
}