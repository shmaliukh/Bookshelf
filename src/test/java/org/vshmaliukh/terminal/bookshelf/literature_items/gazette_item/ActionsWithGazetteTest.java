package org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.terminal.bookshelf.Shelf;
import org.vshmaliukh.terminal.services.ItemSorterService;
import org.vshmaliukh.terminal.services.Utils;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.GazetteHandler.GAZETTE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.GazetteHandler.GAZETTE_COMPARATOR_BY_PAGES;

class ActionsWithGazetteTest {

    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);

    Gazette gazette1 = new Gazette("noNameGazette1", 1, false);
    Gazette gazette2 = new Gazette("noNameGazette2", 2, false);
    Gazette gazette3 = new Gazette("noNameGazette3", 3, true);

    Gazette expectedGazette1 = new Gazette("noNameGazette1", 1, false);
    Gazette expectedGazette2 = new Gazette("noNameGazette2", 2, false);

    /**
     * The magazine3 is not expected to be included for sorting
     */
    int expectedArraySize = 3;

    Shelf shelf1 = new Shelf(printWriter);

    /*
      Adding gazettes in revers order
     */ {
        shelf1.addLiteratureObject(gazette3);
        shelf1.addLiteratureObject(gazette2);
        shelf1.addLiteratureObject(gazette1);
    }

    @Test
    @DisplayName("test printable stings of sorted Magazines by Name")
    void printSortedGazettesByName() {
        List<Gazette> sortedGazettesByName =
                new ItemSorterService<>(Utils.getItemsByType(Gazette.class, shelf1.getAllLiteratureObjects()))
                        .getSortedLiterature(GAZETTE_COMPARATOR_BY_NAME);

        assertEquals(expectedArraySize, sortedGazettesByName.size());
        assertEquals(expectedGazette1.toString(), sortedGazettesByName.get(0).toString());
        assertEquals(expectedGazette2.toString(), sortedGazettesByName.get(1).toString());
    }

    @Test
    @DisplayName("test printable stings of sorted Gazettes by Pages")
    void printSortedGazettesByPages() {
        List<Gazette> sortedGazettesByPages =
                new ItemSorterService<>(Utils.getItemsByType(Gazette.class, shelf1.getAllLiteratureObjects()))
                        .getSortedLiterature(GAZETTE_COMPARATOR_BY_PAGES);

        assertEquals(expectedArraySize, sortedGazettesByPages.size());
        assertEquals(expectedGazette1.toString(), sortedGazettesByPages.get(0).toString());
        assertEquals(expectedGazette2.toString(), sortedGazettesByPages.get(1).toString());
    }
}