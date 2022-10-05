package org.vshmaliukh.shelf.literature_items;

import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.shelf.AbstractUI;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class ItemHandler<T extends Item> implements SqlItemBaseStatementInterface<T>, MySqlItemStatementInterface, SqlLiteItemStatementInterface, ItemWebIntegrationInterface<T> {

    protected ItemHandler() {
    }

    public abstract List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    public abstract List<MenuItemForSorting<T>> getSortingMenuList();

    public void printSortingMenu(PrintWriter printWriter) {
        printWriter.println(AbstractUI.CHOOSE_TYPE_OF_SORTING);
        for (MenuItemForSorting<T> menuItemForSorting : getSortingMenuList()) {
            printWriter.println(menuItemForSorting);
        }
        printWriter.println(AbstractUI.ENTER_ANOTHER_VALUE_TO_RETURN);
    }

    public List<T> clarificationForSortingItems(List<T> items, int userChoice, PrintWriter printWriter) {
        if (items.isEmpty()) {
            printWriter.println(AbstractUI.NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING);
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, items);
        }
        return items;
    }

    public abstract T getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter);

    public abstract T getRandomItem(Random random);

    public abstract Map<String, String> convertItemToMapOfString(T item);
}
