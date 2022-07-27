package org.vshmaliukh.handlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.menus.MenuItemForSorting;
import org.vshmaliukh.menus.MenuItemWithInfoAboutType;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public interface ItemHandler<T extends Item> {

    ConvertorToString getConvertorToString();

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
}
