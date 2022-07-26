package org.vshmaliukh.menus;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.handlers.ItemHandlers.MagazineHandler.MAGAZINE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.handlers.ItemHandlers.MagazineHandler.MAGAZINE_COMPARATOR_BY_PAGES;

public class MenuForSortingMagazines {

    public static final List<MenuItem> menuForSortingMagazinesItems = new ArrayList();

    static {
        menuForSortingMagazinesItems.add(new MenuItem(1, "Sort by 'name' value", MAGAZINE_COMPARATOR_BY_NAME));
        menuForSortingMagazinesItems.add(new MenuItem(2, "Sort by 'pages' value", MAGAZINE_COMPARATOR_BY_PAGES));
    }

    public static void printMenu(PrintWriter printWriter) {
        printWriter.println("Choose type of sorting:");
        menuForSortingMagazinesItems.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }
}
