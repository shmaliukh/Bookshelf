package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenus {

    private GeneratedMenus() {
    }

    public static final List<MenuItemWithInfoAboutType> addingMenuItems;
    public static final List<MenuItemWithInfoAboutType> sortingMenuItems;

    static {
        List<MenuItemWithInfoAboutType> addingMenu = new ArrayList<>();
        int index = 1;
        for (Class<? extends Item> typeName : uniqueTypeNames) {
            addingMenu.add(new MenuItemWithInfoAboutType<>(index++, "Add new " + typeName.getSimpleName() + " item to Shelf", typeName));
            addingMenu.add(new MenuItemWithInfoAboutType<>(index++, "Add random " + typeName.getSimpleName() + " item to Shelf", typeName));
        }
        addingMenuItems = Collections.unmodifiableList(addingMenu);
    }

    static {
        List<MenuItemWithInfoAboutType> sortingMenu = new ArrayList<>();
        int index = 1;
        for (Class<? extends Item> typeName : uniqueTypeNames) {
            sortingMenu.add(new MenuItemWithInfoAboutType<>(index++, "Sort " + typeName.getSimpleName() + " items by value...", typeName));
        }
        sortingMenuItems = Collections.unmodifiableList(sortingMenu);
    }

    public static void printAddingMenu(PrintWriter printWriter) {
        printMenu(printWriter, addingMenuItems);
    }

    public static void printSortingMenu(PrintWriter printWriter) {
        printMenu(printWriter, sortingMenuItems);
    }

    private static void printMenu(PrintWriter printWriter, List<MenuItemWithInfoAboutType> sortingMenuItems) {
        printWriter.println("Enter number of command you wand to execute: (program ignores all not number symbols)");
        sortingMenuItems.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }
}

