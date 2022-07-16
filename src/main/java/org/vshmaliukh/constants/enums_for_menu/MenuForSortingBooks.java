package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.vshmaliukh.constants.ConstantsForTerminal.WRONG_INPUT;

public enum MenuForSortingBooks {

    SORT_BOOKS_BY_NAME(1, "Sort by 'name' value"),
    SORT_BOOKS_BY_AUTHOR(2, "Sort by 'author' value"),
    SORT_BOOKS_BY_PAGES_NUMBER(3, "Sort by 'page number' value"),
    SORT_BOOKS_BY_DATE_OF_ISSUE(4, "Sort by 'date' value"),
    UNKNOWN(WRONG_INPUT);

    private final int i;
    private final String str;

    MenuForSortingBooks(int i, String str){
        this.i = i;
        this.str = str;
    }

    MenuForSortingBooks(int i){
        this(i, "");
    }

    public static void printMenu(PrintWriter printWriter){
        printWriter.println("Choose type of sorting:");
        MenuForSortingBooks[] values = values();
        for (int j = 0; j < values.length - 1; j++) {
            MenuForSortingBooks value = values[j];
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }

    public static MenuForSortingBooks getByIndex(int index){
        return Arrays
                .stream(values())
                .filter(e -> e.i == index)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
