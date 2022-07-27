package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.menus.menu_items.MenuItemWithInfoAboutType;

import java.io.PrintWriter;
import java.util.*;

public abstract class GeneratedMenu {

    List<MenuItemWithInfoAboutType> generatedMenu;

    public List<MenuItemWithInfoAboutType> getMenuItems() {
        return generatedMenu;
    }

    abstract void initMenuItems();

    public void printMenu(PrintWriter printWriter) {
        printWriter.println("Enter number of command you wand to execute: (program ignores all not number symbols)");
        generatedMenu.forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }
}

