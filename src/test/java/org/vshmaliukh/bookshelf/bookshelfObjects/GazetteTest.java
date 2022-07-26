package org.vshmaliukh.bookshelf.bookshelfObjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GazetteTest {

    /**
     * Simple Gazette object for test
     */
    Gazette gazette1 = new Gazette("someValue",0,true);

    @Test
    @DisplayName("test testGetPrintableLineOfLiteratureObject without parameters")
    void testGetPrintableLineOfLiteratureObject_noParameter() {
        String expectedStr= gazette1.toString();

        assertEquals(expectedStr, gazette1.toString());
    }

    @Test
    void getName() {
        String expectedName = "someValue";

        assertEquals(expectedName, gazette1.getName());
    }

    @Test
    void setName() {
        String expectedName = "someName1";
        gazette1.setName("someName1");

        assertEquals(expectedName, gazette1.getName());
    }

    @Test
    void getPagesNumber_0() {
        int expectedNumber = 0;

        assertEquals(expectedNumber, gazette1.getPagesNumber());
    }

    @Test
    void setPagesNumber_0() {
        int expectedNumber = 1;
        gazette1.setPagesNumber(-1);

        assertEquals(expectedNumber, gazette1.getPagesNumber());
    }

    @Test
    void setPagesNumber_1() {
        int expectedNumber = 1;
        gazette1.setPagesNumber(1);

        assertEquals(expectedNumber, gazette1.getPagesNumber());
    }

    @Test
    void isBorrowed() {
        boolean expectedBoolean = true;

        assertEquals(expectedBoolean, gazette1.isBorrowed());
    }

    @Test
    void setBorrowed() {
        boolean expectedBoolean = false;
        gazette1.setBorrowed(false);

        assertEquals(expectedBoolean, gazette1.isBorrowed());
    }

}
