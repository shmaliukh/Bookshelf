package org.vshmaliukh.shelf.literature_items.magazine_item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.console_terminal_app.ConsoleGsonShelfHandler;
import org.vshmaliukh.shelf.literature_items.ItemUtils;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.shelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.shelf.literature_items.magazine_item.MagazineHandler.MAGAZINE_COMPARATOR_BY_PAGES;

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

    ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""),printWriter);

    /*
      Adding magazines in revers order
     */ {
        consoleShelfHandler.addLiteratureObject(magazine3);
        consoleShelfHandler.addLiteratureObject(magazine2);
        consoleShelfHandler.addLiteratureObject(magazine1);
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Name")
    void printSortedMagazinesByName() {
        List<Magazine> sortedMagazinesByName =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Magazine.class, consoleShelfHandler.getShelf().itemsOfShelf),
                        MAGAZINE_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        assertEquals(expectedMagazine1.toString(), sortedMagazinesByName.get(0).toString());
        assertEquals(expectedMagazine2.toString(), sortedMagazinesByName.get(1).toString());
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Pages")
    void printSortedMagazinesByPages() {
        List<Magazine> sortedMagazinesByPages =
                ItemUtils.getSortedLiterature(ItemUtils.getItemsByType(Magazine.class, consoleShelfHandler.getShelf().getAllLiteratureObjects()),
                        MAGAZINE_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        assertEquals(expectedMagazine1.toString(), sortedMagazinesByPages.get(0).toString());
        assertEquals(expectedMagazine2.toString(), sortedMagazinesByPages.get(1).toString());
    }
}