package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;

import java.io.PrintWriter;
import java.util.*;

public abstract class GeneratedMenu {

    public static final String MESSAGE_TO_ENTER = "Enter number of command you wand to execute: (program ignores all not number symbols)";
    public static final String MESSAGE_TO_RETURN = "Enter another value to return";

    List<MenuItemClassType> generatedMenu;

    public List<MenuItemClassType> getMenuItems() {
        return Collections.unmodifiableList(generatedMenu);
    }

    abstract void initMenuItems();

    public void printMenu(PrintWriter printWriter) {
        printWriter.println(MESSAGE_TO_ENTER);
        generatedMenu.forEach(printWriter::println);
        printWriter.println(MESSAGE_TO_RETURN);
    }
}

