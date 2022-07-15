package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;

public enum MenuForSortingMagazines {

    SORT_MAGAZINES_BY_NAME(1, "Sort by 'name' value"),
    SORT_MAGAZINES_BY_AUTHOR(2, "Sort by 'author' value");

    private final int i;
    private final String str;

    MenuForSortingMagazines(int i, String str){
        this.i = i;
        this.str = str;
    }

    public static void printMenu(PrintWriter printWriter){
        printWriter.println("Choose type of sorting:");
        for (MenuForSortingMagazines value : values()) {
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }
}
