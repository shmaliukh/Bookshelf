package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.menus.menu_items.MenuItemWithInfoAboutType;

import java.io.PrintWriter;
import java.util.*;

public abstract class GeneratedMenu {

    List<MenuItemWithInfoAboutType> generatedMenu;
    //public static final List<MenuItemWithInfoAboutType> sortingMenuItems;
    //static {
    //    List<MenuItemWithInfoAboutType> sortingMenu = new ArrayList<>();
    //    int index = 1;
    //    for (Class<? extends Item> typeName : uniqueTypeNames) {
    //        sortingMenu.add(new MenuItemWithInfoAboutType<>(index++, "Sort " + typeName.getSimpleName() + " items by value...", typeName));
    //    }
    //    sortingMenuItems = Collections.unmodifiableList(sortingMenu);

    abstract void initMenuItems();

    public void printMenu(PrintWriter printWriter) {
        printWriter.println("Enter number of command you wand to execute: (program ignores all not number symbols)");
        generatedMenu.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }
}

