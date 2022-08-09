package org.vshmaliukh.services.menus;

import org.vshmaliukh.services.menus.menu_items.MenuItem;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.console_terminal_app.ConsoleShelfHandler.WRONG_INPUT;

public enum MainMenu {

    ADD_NEW_LITERATURE(new MenuItem(1, "Add new Literature object to Shelf")),
    DELETE_LITERATURE(new MenuItem(2, "Delete  Literature object by index from Shelf")),
    BORROW_LITERATURE(new MenuItem(3, "Borrow  Literature object by index from Shelf")),
    ARRIVE_LITERATURE(new MenuItem(4, "Arrive  Literature object by index back to Shelf")),
    SORT_LITERATURE(new MenuItem(5, "Print list of shelf's type of literature sorted by parameter...")),
    PRINT_SHELF(new MenuItem(9, "Print current state of Shelf")),
    EXIT(new MenuItem(0, "Exit")),
    UNKNOWN(new MenuItem(WRONG_INPUT, "Wrong input"));

    final MenuItem menuItem;

    MainMenu(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public static void printMainMenu(PrintWriter printWriter) {
        printWriter.println("Enter number of command you wand to execute: (program ignores all not number symbols)" );
        MainMenu[] values = values();
        for (int j = 0; j < values.length - 1; j++) {
            MainMenu value = values[j];
            printWriter.println(value.getMenuItem() );
        }
    }

    public static List<MainMenu> getAllMenuItems(){
        return Collections.unmodifiableList(Arrays.asList(values()));
    }

    public static MainMenu getByIndex(int index) {
        return Arrays
                .stream(values())
                .filter(e -> e.getMenuItem().getIndex() == index)
                .findFirst()
                .orElse(UNKNOWN);
    }

    @Override
    public String toString() {
        return menuItem.toString();
    }
}
