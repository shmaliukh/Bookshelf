package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.handlers.ItemHandlers.GazetteHandler.GAZETTE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.handlers.ItemHandlers.GazetteHandler.GAZETTE_COMPARATOR_BY_PAGES;

public class MenuForSortingGazettes {

    public static final List<MenuItem> menuForSortingGazettesItems = new ArrayList();

    static {
        menuForSortingGazettesItems.add(new MenuItem(1, "Sort by 'name' value", GAZETTE_COMPARATOR_BY_NAME));
        menuForSortingGazettesItems.add(new MenuItem(2, "Sort by 'pages' value", GAZETTE_COMPARATOR_BY_PAGES));
    }

    public static void printMenu(PrintWriter printWriter) {
        printWriter.println("Choose type of sorting:");
        menuForSortingGazettesItems.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }
}
