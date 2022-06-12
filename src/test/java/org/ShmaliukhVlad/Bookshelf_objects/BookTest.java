package org.ShmaliukhVlad.Bookshelf_objects;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void getName() {
        System.out.printf( "%s%-30s" ,"test ", "getName ");
        String expectedName = "someName";
        Book book1 = new Book("someName",1,false,"NoAuthor1", LocalDate.now());

        assertEquals(book1.getName(), expectedName);
        System.out.println("is successful");
    }

    @Test
    void setName() {
        System.out.printf( "%s%-30s" ,"test ", "setName ");
        String expectedStr = "someName";
        Book book1 = new Book("1",1,false,"NoAuthor1",LocalDate.now());
        book1.setName("someName");

        assertEquals(book1.getName(), expectedStr);
        System.out.println("is successful");
    }

    @Test
    void getPagesNumber() {
        System.out.printf( "%s%-30s" ,"test ", "getPagesNumber ");
        int expectedNumber = 1;
        Book book1 = new Book("someName",1,false,"NoAuthor1",LocalDate.now());

        assertEquals(book1.getPagesNumber(), expectedNumber);
        System.out.println("is successful");
    }

    @Test
    void setPagesNumber_0() {
        System.out.printf( "%s%-30s" ,"test ", "setPagesNumber(1) ");
        int expectedNumber = 1;
        Book book1 = new Book("someName",0,false,"NoAuthor1",LocalDate.now());
        book1.setPagesNumber(1);

        assertEquals(book1.getPagesNumber(), expectedNumber);
        System.out.println("is successful");
    }
    @Test
    void setPagesNumber_1() {
        System.out.printf( "%s%-30s" ,"test ", "setPagesNumber(-1) ");
        int expectedNumber = 0;
        Book book1 = new Book("someName",0,false,"NoAuthor1",LocalDate.now());
        book1.setPagesNumber(-1);

        assertEquals(book1.getPagesNumber(), expectedNumber);
        System.out.println("is successful");
    }

    @Test
    void isBorrowed() {
        System.out.printf( "%s%-30s" ,"test ", "isBorrowed ");
        boolean expectedBoolean = false;
        Book book1 = new Book("someName",1,false,"NoAuthor1",LocalDate.now());

        assertEquals(book1.isBorrowed(), expectedBoolean);
        System.out.println("is successful");
    }

    @Test
    void setBorrowed() {
        System.out.printf( "%s%-30s" ,"test ", "setBorrowed ");
        boolean expectedBoolean = true;
        Book book1 = new Book("someName",0,false,"NoAuthor1",LocalDate.now());
        book1.setBorrowed(true);

        assertEquals(book1.isBorrowed(), expectedBoolean);
        System.out.println("is successful");
    }

    @Test
    void getAuthor() {
        System.out.printf( "%s%-30s" ,"test ", "getAuthor ");
        String expectedAuthor = "someAuthor";
        Book book1 = new Book("someName",1,false,"someAuthor",LocalDate.now());

        assertEquals(book1.getAuthor(),  expectedAuthor);
        System.out.println("is successful");
    }

    @Test
    void setAuthor() {
        System.out.printf( "%s%-30s" ,"test ", "setAuthor ");
        String expectedAuthor = "someAuthor";
        Book book1 = new Book("1",1,false,"NoAuthor1",LocalDate.now());
        book1.setAuthor("someAuthor");

        assertEquals(book1.getAuthor(), expectedAuthor);
        System.out.println("is successful");
    }

    @Test
    void getIssuanceDate() {
        System.out.printf( "%s%-30s" ,"test ", "getIssuanceDate ");
        LocalDate expectedDate = LocalDate.now();
        Book book1 = new Book("someName",1,false,"someAuthor",LocalDate.now());

        assertEquals(book1.getIssuanceDate(),  expectedDate);
        System.out.println("is successful");
    }

    @Test
    void setIssuanceDate() {
        System.out.printf( "%s%-30s" ,"test ", "setIssuanceDate ");
        LocalDate expectedDate = LocalDate.now();
        Book book1 = new Book("1",1,false,"NoAuthor1", LocalDate.now());
        book1.setIssuanceDate(LocalDate.now());

        assertEquals(book1.getIssuanceDate(), expectedDate);
        System.out.println("is successful");
    }

    @Test
    void compareTo() {
    }
}