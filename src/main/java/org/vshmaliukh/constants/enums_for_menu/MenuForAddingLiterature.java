package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.vshmaliukh.Terminal.WRONG_INPUT;


public enum MenuForAddingLiterature {

    ADD_CUSTOM_MAGAZINE(1, "Magazine"),
    ADD_CUSTOM_BOOK(2, "Book"),
    ADD_CUSTOM_GAZETTE(3, "Gazette"),
    ADD_RANDOM_MAGAZINE(4, "Random Magazine"),
    ADD_RANDOM_BOOK(5, "Random Book"),
    ADD_RANDOM_GAZETTE(6, "Random Gazette"),
    UNKNOWN(WRONG_INPUT);

    public final int i;
    public final String str;

    MenuForAddingLiterature(int i, String str) {
        this.i = i;
        this.str = str;
    }

    MenuForAddingLiterature(int i ){
        this(i,"");
    }

    public static void printMenu(PrintWriter printWriter) {
        printWriter.println("Choose type of literature you want to add:");
        MenuForAddingLiterature[] values = values();
        for (int j = 0, valuesLength = values.length - 1; j < valuesLength; j++) {
            MenuForAddingLiterature value = values[j];
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }

    public static MenuForAddingLiterature getByIndex(int index){
        return Arrays
                .stream(values())
                .filter(e -> e.i == index)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
