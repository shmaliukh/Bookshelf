package org.vshmaliukh.shelf.literature_items;

import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.console_terminal_app.ConsoleUI.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;

public abstract class ItemHandler<T extends Item> implements SqlItemBaseStatementInterface<T>, MySqlItemStatementInterface, SqlLiteItemStatementInterface, ItemWebIntegrationInterface<T> {

    protected ItemHandler() {
    }

    public abstract List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    public abstract List<MenuItemForSorting<T>> getSortingMenuList();

    public void printSortingMenu(PrintWriter printWriter) {
        printWriter.println(CHOOSE_TYPE_OF_SORTING);
        for (MenuItemForSorting<T> menuItemForSorting : getSortingMenuList()) {
            printWriter.println(menuItemForSorting);
        }
        printWriter.println(ENTER_ANOTHER_VALUE_TO_RETURN);
    }

    public List<T> clarificationForSortingItems(List<T> items, int userChoice, PrintWriter printWriter) {
        if (items.isEmpty()) {
            printWriter.println(NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING);
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, items);
        }
        return items;
    }

    public abstract T getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter);

    public abstract T getRandomItem(Random random);

    public abstract Map<String, String> convertItemToListOfString(T item);
}
