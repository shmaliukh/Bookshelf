package org.ShmaliukhVlad.bookshelf.bookshelfObjects;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.ShmaliukhVlad.constants.ConstantValues.*;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    /**
     * Simple Book object for test
     */
    Book book1 = new Book("someName", 1, false, "someAuthor", new Date());
    Book book2 = new Book("someName2", 2, true, "someAuthor2", new Date());

    @Test
    @DisplayName("test testGetPrintableLineOfLiteratureObject with parameter SORT_BOOKS_BY_PAGES_NUMBER")
    @Description("Should return special printable line when sorting by pages")
    void testGetPrintableLineOfLiteratureObject_whenSortByPages() {
        String expectedStr =
                "Book {" +
                        " pagesNumber=" + 1 +
                        ",  name='" + "someName" + '\'' +
                        ",  author='" + "someAuthor" + '\'' +
                        ",  issuanceDate=" + new Date() +
                        ",  isBorrowed=" + false +
                        " }\n";

        assertEquals(expectedStr, book1.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_PAGES_NUMBER));
    }

    @Test
    @DisplayName("test testGetPrintableLineOfLiteratureObject with parameter SORT_BOOKS_BY_NAME")
    @Description("Should return special printable line when sorting by name")
    void testGetPrintableLineOfLiteratureObject_whenSortByName() {
        String expectedStr =
                "Book {" +
                        " name='" + "someName" + '\'' +
                        ",  pagesNumber=" + 1 +
                        ",  author='" + "someAuthor" + '\'' +
                        ",  issuanceDate=" + new Date() +
                        ",  isBorrowed=" + false +
                        " }\n";

        assertEquals(expectedStr, book1.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_NAME));
    }

    @Test
    @DisplayName("test testGetPrintableLineOfLiteratureObject with parameter SORT_BOOKS_BY_AUTHOR")
    @Description("Should return special printable line when sorting by author")
    void testGetPrintableLineOfLiteratureObject_whenSortByAuthor() {
        String expectedStr =
                "Book {" +
                        " author='" + "someAuthor" + '\'' +
                        ",  name='" + "someName" + '\'' +
                        ",  pagesNumber=" + 1 +
                        ",  issuanceDate=" + new Date() +
                        ",  isBorrowed=" + false +
                        " }\n";

        assertEquals(expectedStr, book1.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_AUTHOR));
    }

    @Test
    @DisplayName("test testGetPrintableLineOfLiteratureObject with parameter SORT_BOOKS_BY_DATE_OF_ISSUE")
    @Description("Should return special printable line when sorting by date of issue")
    void testGetPrintableLineOfLiteratureObject_whenSortByDateOfIssue() {
        String expectedStr =
                "Book {" +
                        " issuanceDate=" + new Date() +
                        ",  name='" + "someName" + '\'' +
                        ",  author='" + "someAuthor" + '\'' +
                        ",  pagesNumber=" + 1 +
                        ",  isBorrowed=" + false +
                        " }\n";

        assertEquals(expectedStr, book1.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_DATE_OF_ISSUE));
    }


    @Test
    @DisplayName("test getName")
    void getName() {
        String expectedName = "someName";

        assertEquals(expectedName, book1.getName());
    }

    @Test
    @DisplayName("test setName")
    void setName() {
        String expectedStr = "someName";
        book1.setName("someName");

        assertEquals(expectedStr, book1.getName());
    }

    @Test
    @DisplayName("test getPagesNumber")
    void getPagesNumber() {
        int expectedNumber = 1;

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    @DisplayName("test setPagesNumber (0)")
    @Description("Pages number can be 0")
    void setPagesNumber_0() {
        int expectedNumber = 0;
        book1.setPagesNumber(0);

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    @DisplayName("test setPagesNumber (-1)")
    @Description("Pages number can not be lower than 0")
    void setPagesNumber_1() {
        int expectedNumber = 1;
        book1.setPagesNumber(-1);

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    @DisplayName("test setPagesNumber (1)")
    void setPagesNumber_2() {
        int expectedNumber = 1;
        book1.setPagesNumber(1);

        assertEquals(expectedNumber, book1.getPagesNumber());
    }

    @Test
    @DisplayName("test isBorrowed")
    void isBorrowed() {
        boolean expectedBoolean = false;

        assertEquals(expectedBoolean, book1.isBorrowed());
    }

    @Test
    @DisplayName("test setBorrowed")
    void setBorrowed() {
        boolean expectedBoolean = true;
        book1.setBorrowed(true);

        assertEquals(expectedBoolean, book1.isBorrowed());
    }

    @Test
    @DisplayName("test getAuthor")
    void getAuthor() {
        String expectedAuthor = "someAuthor";

        assertEquals(expectedAuthor, book1.getAuthor());
    }

    @Test
    @DisplayName("test setAuthor")
    void setAuthor() {
        String expectedAuthor = "someAuthor1";
        book1.setAuthor("someAuthor1");

        assertEquals(expectedAuthor, book1.getAuthor());
    }

    @Test
    @DisplayName("test getIssuanceDate")
    void getIssuanceDate() {
        Date expectedDate = new Date();
        assertEquals(expectedDate, book1.getIssuanceDate());
    }

    @Test
    @DisplayName("test setIssuanceDate")
    void setIssuanceDate() {
        Date expectedDate = new Date(123456);
        book2.setIssuanceDate(new Date(123456));
        assertEquals(expectedDate, book2.getIssuanceDate());

    }
}