package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;

public enum MenuForSortingBooks {

    SORT_BOOKS_BY_NAME(1, "Sort by 'name' value"),
    SORT_BOOKS_BY_AUTHOR(2, "Sort by 'author' value"),
    SORT_BOOKS_BY_PAGES_NUMBER(3, "Sort by 'page number' value"),
    SORT_BOOKS_BY_DATE_OF_ISSUE(4, "Sort by 'date' value");

    private final int i;
    private final String str;

    MenuForSortingBooks(int i, String str){
        this.i = i;
        this.str = str;
    }

    public static void printMenu(PrintWriter printWriter){
        printWriter.println("Choose type of sorting:");
        for (MenuForSortingBooks value : values()) {
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }
}
