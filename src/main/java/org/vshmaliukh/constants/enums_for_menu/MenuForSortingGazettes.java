package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.vshmaliukh.ConstantsForTerminal.WRONG_INPUT;

public enum MenuForSortingGazettes {
    SORT_GAZETTES_BY_NAME(1, "Sort by 'name' value"),
    SORT_GAZETTES_BY_PAGES(2, "Sort by 'pages' value"),
    UNKNOWN(WRONG_INPUT);

    private final int i;
    private final String str;

    MenuForSortingGazettes(int i, String str){
        this.i = i;
        this.str = str;
    }

    MenuForSortingGazettes(int i){
        this(i, "");
    }

    public static void printMenu(PrintWriter printWriter){
        printWriter.println("Choose type of sorting:");
        MenuForSortingGazettes[] values = values();
        for (int j = 0; j < values.length - 1 ; j++) {
            MenuForSortingGazettes value = values[j];
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }

    public static MenuForSortingGazettes getByIndex(int index){
        return Arrays
                .stream(values())
                .filter(e -> e.i == index)
                .findFirst()
                .orElse(UNKNOWN);
    }

}
