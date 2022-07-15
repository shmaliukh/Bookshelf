package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.vshmaliukh.constants.ConstantsForTerminal.WRONG_INPUT;

public enum MenuForSortingMagazines {

    SORT_MAGAZINES_BY_NAME(1, "Sort by 'name' value"),
    SORT_MAGAZINES_BY_PAGES(2, "Sort by 'pages' value"),
    UNKNOWN(WRONG_INPUT);

    private final int i;
    private final String str;

    MenuForSortingMagazines(int i, String str){
        this.i = i;
        this.str = str;
    }

    MenuForSortingMagazines(int i){
        this(i, "");
    }

    public static void printMenu(PrintWriter printWriter){
        printWriter.println("Choose type of sorting:");
        for (MenuForSortingMagazines value : values()) {
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }

    public static MenuForSortingMagazines getByIndex(int index){
        return Arrays
                .stream(values())
                .filter(e -> e.i == index)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
