package org.vshmaliukh.menus;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.handlers.ItemHandlers.BookHandler.*;

public class MenuForSortingBooks {

    private MenuForSortingBooks() {
    }

    public static final List<MenuItem> menuForSortingBooksItems = new ArrayList();

    static {
        menuForSortingBooksItems.add(new MenuItem(1, "Sort by 'name' value", BOOK_COMPARATOR_BY_NAME));
        menuForSortingBooksItems.add(new MenuItem(2, "Sort by 'author' value", BOOK_COMPARATOR_BY_AUTHOR));
        menuForSortingBooksItems.add(new MenuItem(3, "Sort by 'page number' value", BOOK_COMPARATOR_BY_PAGES));
        menuForSortingBooksItems.add(new MenuItem(4, "Sort by 'date' value", BOOK_COMPARATOR_BY_DATE));
    }

    public static void printMenu(PrintWriter printWriter) {
        printWriter.println("Choose type of sorting:");
        menuForSortingBooksItems.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }
}
