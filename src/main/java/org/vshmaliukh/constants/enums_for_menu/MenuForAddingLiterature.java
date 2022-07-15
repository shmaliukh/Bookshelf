package org.vshmaliukh.constants.enums_for_menu;

import java.io.PrintWriter;

public enum MenuForAddingLiterature {

    ADD_CUSTOM_MAGAZINE(1, "Magazine"),
    ADD_CUSTOM_BOOK(2, "Book"),
    ADD_RANDOM_MAGAZINE(3, "Random Magazine"),
    ADD_RANDOM_BOOK(4, "Random Book");
    //UNKNOWN(-1);

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
        for (MenuForAddingLiterature value : values()) {
            printWriter.println(value.i + " - " + value.str);
        }
        printWriter.println("Enter another value to return");
    }
    //public static MenuForAddingLiterature getByIndex(int index){
    //    return Arrays
    //            .stream(values())
    //            .filter(e -> e.i == index)
    //            .findFirst()
    //            .orElse(UNKNOWN);
    //}
}
