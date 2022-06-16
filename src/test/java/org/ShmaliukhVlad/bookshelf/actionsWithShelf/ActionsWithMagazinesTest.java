package org.ShmaliukhVlad.bookshelf.actionsWithShelf;

import jdk.jfr.Description;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActionsWithMagazinesTest {
    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,false);
    Magazine magazine3 = new Magazine("noNameMagazine3",3,true);

    Magazine expectedMagazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine expectedMagazine2 = new Magazine("noNameMagazine2",2,false);

    /**
     * The magazine3 is not expected to be included for sorting
     */
    int expectedArraySize = 2;

    Shelf shelf1 = new Shelf();

    /*
      Adding magazines in revers order
     */
    {
        shelf1.addLiteratureObject(magazine3);
        shelf1.addLiteratureObject(magazine2);
        shelf1.addLiteratureObject(magazine1);
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Name")
    @Description("Simple test for getting info about sorted available magazines in shelf by name (magazines added in revers order)." +
            "Operate with getPrintableLineOfLiteratureObject() method")
    void printSortedMagazinesByName() {
        ArrayList<Magazine> sortedMagazinesByName = shelf1.getSortedMagazinesByName();

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        assertEquals(expectedMagazine1.getPrintableLineOfLiteratureObject(), sortedMagazinesByName.get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine2.getPrintableLineOfLiteratureObject(), sortedMagazinesByName.get(1).getPrintableLineOfLiteratureObject());
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Pages")
    @Description("Simple test for getting info about sorted available magazines in shelf by pages (magazines added in revers order)." +
            "Operate with getPrintableLineOfLiteratureObject() method")
    void printSortedMagazinesByPages() {
        ArrayList<Magazine> sortedMagazinesByPages = shelf1.getSortedMagazinesByPages();

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        assertEquals(expectedMagazine1.getPrintableLineOfLiteratureObject(), sortedMagazinesByPages.get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine2.getPrintableLineOfLiteratureObject(), sortedMagazinesByPages.get(1).getPrintableLineOfLiteratureObject());
    }
}