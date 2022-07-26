package org.vshmaliukh.constants.enums_for_menu;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.handlers.ItemHandlerProvider.uniqueTypeNames;

public class Menu {

    private Menu() {
    }

    public static final List<MenuItem> mainMenuItems = new ArrayList();
    public static final List<MenuItem> addingMenuItems = new ArrayList();
    public static final List<MenuItem> sortingMenuItems = new ArrayList();

    static {
        mainMenuItems.add(new MenuItem(1, "Add new Literature object to Shelf"));
        mainMenuItems.add(new MenuItem(2, "Delete  Literature object by index from Shelf"));
        mainMenuItems.add(new MenuItem(3, "Borrow  Literature object by index from Shelf"));
        mainMenuItems.add(new MenuItem(4, "Arrive  Literature object by index back to Shelf"));
        mainMenuItems.add(new MenuItem(5, "Print list of shelf's type of literature sorted by parameter...")); // TODO rename item str
        mainMenuItems.add(new MenuItem(9, "Print current state of Shelf"));
        mainMenuItems.add(new MenuItem(0, "Exit"));
    }

    static {
        int index = 1;
        for (Class<? extends Item> typeName : uniqueTypeNames) {
            addingMenuItems.add(new MenuItem(index, "Add new " + typeName.getSimpleName() + " item to Shelf", typeName));
            index++;
            addingMenuItems.add(new MenuItem(index, "Add random " + typeName.getSimpleName() + " item to Shelf", typeName));
            index++;
        }
    }

    static {
        int index = 1;
        for (Class<? extends Item> typeName : uniqueTypeNames) {
            sortingMenuItems.add(new MenuItem(index, "Sort " + typeName.getSimpleName() + " items by value...", typeName));
            index++;
        }
    }

    public static void printMainMenu(PrintWriter printWriter) {
        printMenu(printWriter, mainMenuItems);
    }

    public static void printAddingMenu(PrintWriter printWriter) {
        printMenu(printWriter, addingMenuItems);
    }

    public static void printSortingMenu(PrintWriter printWriter) {
        printMenu(printWriter, sortingMenuItems);
    }

    private static void printMenu(PrintWriter printWriter, List<MenuItem> sortingMenuItems) {
        printWriter.println("Enter number of command you wand to execute: (program ignores all not number symbols)");
        sortingMenuItems.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }

}

