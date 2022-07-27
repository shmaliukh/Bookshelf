package org.vshmaliukh.terminal.bookshelf.literature_items;

import org.vshmaliukh.terminal.menus.MenuItemForSorting;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Random;

public interface ItemHandler<T extends Item> {

    List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    List<MenuItemForSorting> getSortingMenuList();

    default void printSortingMenu(PrintWriter printWriter){
        printWriter.println("Choose type of sorting:");
        getSortingMenuList().forEach(printWriter::println);
        printWriter.println("Enter another value to return");
    }

    List<T> clarificationForSortingItems(List<T> items, int userChoice, PrintWriter printWriter);

    T getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter);

    T getRandomItem(Random random);

    Map<String, String> convertItemToListOfString(T item);
}
