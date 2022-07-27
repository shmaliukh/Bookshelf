package org.vshmaliukh.terminal.menus;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.vshmaliukh.terminal.Terminal.WRONG_INPUT;

public enum MainMenu {

    ADD_NEW_LITERATURE(1, "Add new Literature object to Shelf"),
    DELETE_LITERATURE(2, "Delete  Literature object by index from Shelf"),
    BORROW_LITERATURE(3, "Borrow  Literature object by index from Shelf"),
    ARRIVE_LITERATURE(4, "Arrive  Literature object by index back to Shelf"),
    SORT_LITERATURE(5, "Print list of shelf's type of literature sorted by parameter..."),
    PRINT_SHELF(9, "Print current state of Shelf"),
    EXIT(0, "Exit"),
    UNKNOWN(WRONG_INPUT);

    private final int i;
    private final String str;

    MainMenu(int i, String str){
        this.i = i;
        this.str = str;
    }

    MainMenu(int i){
        this(i, "");
    }

    public static void printMainMenu(PrintWriter printWriter){
        printWriter.println("Enter number of command you wand to execute: (program ignores all not number symbols)");
        MainMenu[] values = values();
        for (int j = 0; j < values.length - 1; j++) {
            MainMenu value = values[j];
            printWriter.println(value.i + " - " + value.str);
        }
    }

    public static MainMenu getByIndex(int index){
        return Arrays
                .stream(values())
                .filter(e -> e.i == index)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
