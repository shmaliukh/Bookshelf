package org.ShmaliukhVlad.Bookshelf_objects;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    @DisplayName("test getName")
    void getName() {
        String expectedName = "someName";
        Book book1 = new Book("someName",1,false,"NoAuthor1", LocalDate.now());

        assertEquals(book1.getName(), expectedName);
    }

    @Test
    @DisplayName("test setName")
    void setName() {
        String expectedStr = "someName";
        Book book1 = new Book("1",1,false,"NoAuthor1",LocalDate.now());
        book1.setName("someName");

        assertEquals(book1.getName(), expectedStr);
    }

    @Test
    @DisplayName("test getPagesNumber")
    void getPagesNumber() {
        int expectedNumber = 1;
        Book book1 = new Book("someName",1,false,"NoAuthor1",LocalDate.now());

        assertEquals(book1.getPagesNumber(), expectedNumber);
    }

    @Test
    @DisplayName("test  setPagesNumber (1)")
    void setPagesNumber_0() {
        int expectedNumber = 1;
        Book book1 = new Book("someName",0,false,"NoAuthor1",LocalDate.now());
        book1.setPagesNumber(1);

        assertEquals(book1.getPagesNumber(), expectedNumber);
    }
    @Test
    @DisplayName("test  setPagesNumber (-1)")
    void setPagesNumber_1() {
        int expectedNumber = 0;
        Book book1 = new Book("someName",0,false,"NoAuthor1",LocalDate.now());
        book1.setPagesNumber(-1);

        assertEquals(book1.getPagesNumber(), expectedNumber);
    }

    @Test
    @DisplayName("test isBorrowed")
    void isBorrowed() {
        boolean expectedBoolean = false;
        Book book1 = new Book("someName",1,false,"NoAuthor1",LocalDate.now());

        assertEquals(book1.isBorrowed(), expectedBoolean);
    }

    @Test
    @DisplayName("test setBorrowed")
    void setBorrowed() {
        boolean expectedBoolean = true;
        Book book1 = new Book("someName",0,false,"NoAuthor1",LocalDate.now());
        book1.setBorrowed(true);

        assertEquals(book1.isBorrowed(), expectedBoolean);
    }

    @Test
    @DisplayName("test getAuthor")
    void getAuthor() {
        String expectedAuthor = "someAuthor";
        Book book1 = new Book("someName",1,false,"someAuthor",LocalDate.now());

        assertEquals(book1.getAuthor(),  expectedAuthor);
    }

    @Test
    @DisplayName("test setAuthor")
    void setAuthor() {
        String expectedAuthor = "someAuthor";
        Book book1 = new Book("1",1,false,"NoAuthor1",LocalDate.now());
        book1.setAuthor("someAuthor");

        assertEquals(book1.getAuthor(), expectedAuthor);
    }

    @Test
    @DisplayName("test getIssuanceDate")
    void getIssuanceDate() {
        LocalDate expectedDate = LocalDate.now();
        Book book1 = new Book("someName",1,false,"someAuthor",LocalDate.now());

        assertEquals(book1.getIssuanceDate(),  expectedDate);
    }

    @Test
    @DisplayName("test setIssuanceDate")
    void setIssuanceDate() {
        System.out.printf( "%s%-30s" ,"test ", "setIssuanceDate ");
        LocalDate expectedDate = LocalDate.now();
        Book book1 = new Book("1",1,false,"NoAuthor1", LocalDate.now());
        book1.setIssuanceDate(LocalDate.now());

        assertEquals(book1.getIssuanceDate(), expectedDate);
        System.out.println("is successful");
    }
}