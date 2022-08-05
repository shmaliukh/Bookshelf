package org.vshmaliukh.bookshelf.literature_items.magazine_item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.console_terminal.services.Utils;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.bookshelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.bookshelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_PAGES;

class ActionsWithMagazineTest {

    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);

    Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine magazine2 = new Magazine("noNameMagazine2", 2, false);
    Magazine magazine3 = new Magazine("noNameMagazine3", 3, true);

    Magazine expectedMagazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine expectedMagazine2 = new Magazine("noNameMagazine2", 2, false);

    /**
     * The magazine3 is not expected to be included for sorting
     */
    int expectedArraySize = 3;

    Shelf shelf1 = new Shelf(printWriter);

    /*
      Adding magazines in revers order
     */ {
        shelf1.addLiteratureObject(magazine3);
        shelf1.addLiteratureObject(magazine2);
        shelf1.addLiteratureObject(magazine1);
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Name")
    void printSortedMagazinesByName() {
        List<Magazine> sortedMagazinesByName =
                Utils.getSortedLiterature(Utils.getItemsByType(Magazine.class, shelf1.getAllLiteratureObjects()),
                        MAGAZINE_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        assertEquals(expectedMagazine1.toString(), sortedMagazinesByName.get(0).toString());
        assertEquals(expectedMagazine2.toString(), sortedMagazinesByName.get(1).toString());
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Pages")
    void printSortedMagazinesByPages() {
        List<Magazine> sortedMagazinesByPages =
                Utils.getSortedLiterature(Utils.getItemsByType(Magazine.class, shelf1.getAllLiteratureObjects()),
                        MAGAZINE_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        assertEquals(expectedMagazine1.toString(), sortedMagazinesByPages.get(0).toString());
        assertEquals(expectedMagazine2.toString(), sortedMagazinesByPages.get(1).toString());
    }
}