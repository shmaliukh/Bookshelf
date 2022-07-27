package org.vshmaliukh.terminal.bookshelf.literature_items.book_item;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.terminal.bookshelf.literature_items.book_item.Book;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    /**
     * Simple Book object for test
     */
    Book book1 = new Book("someName", 1, false, "someAuthor", new Date());
    Book book2 = new Book("someName2", 2, true, "someAuthor2", new Date());

    @Test
    void getName() {
        String expectedName = "someName";

        assertEquals(expectedName, book1.getName());
    }

    @Test
    void setName() {
        String expectedStr = "someName";
        book1.setName("someName");

        assertEquals(expectedStr, book1.getName());
    }

    @Test
    void getPagesNumber() {
        int expectedNumber = 1;

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    void setPagesNumber_0() {
        int expectedNumber = 0;
        book1.setPagesNumber(0);

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    void setPagesNumber_1() {
        int expectedNumber = 1;
        book1.setPagesNumber(-1);

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    void setPagesNumber_2() {
        int expectedNumber = 1;
        book1.setPagesNumber(1);

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    void isBorrowed() {
        boolean expectedBoolean = false;

        assertEquals(expectedBoolean, book1.isBorrowed());
    }

    @Test
    void setBorrowed() {
        boolean expectedBoolean = true;
        book1.setBorrowed(true);

        assertEquals(expectedBoolean, book1.isBorrowed());
    }

    @Test
    void getAuthor() {
        String expectedAuthor = "someAuthor";

        assertEquals(expectedAuthor, book1.getAuthor());
    }

    @Test
    void setAuthor() {
        String expectedAuthor = "someAuthor1";
        book1.setAuthor("someAuthor1");

        assertEquals(expectedAuthor, book1.getAuthor());
    }

    @Test
    void setIssuanceDate() {
        Date expectedDate = new Date(123456);
        book2.setIssuanceDate(new Date(123456));
        assertEquals(expectedDate, book2.getIssuanceDate());

    }
}