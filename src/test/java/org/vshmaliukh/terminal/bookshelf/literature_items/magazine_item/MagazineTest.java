package org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.Magazine;

import static org.junit.jupiter.api.Assertions.*;

class MagazineTest {
    /**
     * Simple Magazine object for test
     */
    Magazine magazine1 = new Magazine("someValue",0,true);

    @Test
    @DisplayName("test testGetPrintableLineOfLiteratureObject without parameters")
    void testGetPrintableLineOfLiteratureObject_noParameter() {
        String expectedStr= magazine1.toString();

        assertEquals(expectedStr, magazine1.toString());
    }

    @Test
    void getName() {
        String expectedName = "someValue";

        assertEquals(expectedName, magazine1.getName());
    }

    @Test
    void setName() {
        String expectedName = "someName1";
        magazine1.setName("someName1");

        assertEquals(expectedName, magazine1.getName());
    }

    @Test
    void getPagesNumber_0() {
        int expectedNumber = 0;

        assertEquals(expectedNumber, magazine1.getPagesNumber());
    }

    @Test
    void setPagesNumber_0() {
        int expectedNumber = 1;
        magazine1.setPagesNumber(-1);

        assertEquals(expectedNumber, magazine1.getPagesNumber());
    }

    @Test
    void setPagesNumber_1() {
        int expectedNumber = 1;
        magazine1.setPagesNumber(1);

        assertEquals(expectedNumber, magazine1.getPagesNumber());
    }

    @Test
    void isBorrowed() {
        boolean expectedBoolean = true;

        assertEquals(expectedBoolean, magazine1.isBorrowed());
    }

    @Test
    void setBorrowed() {
        boolean expectedBoolean = false;
        magazine1.setBorrowed(false);

        assertEquals(expectedBoolean, magazine1.isBorrowed());
    }
}