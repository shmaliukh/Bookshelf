package org.vshmaliukh.shelf.literature_items;

import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemUtils.COMA_DELIMITER;
import static org.vshmaliukh.shelf.literature_items.ItemUtils.VALUE_DELIMITER;

public interface ItemHandler<T extends Item> {

    String CHOOSE_TYPE_OF_SORTING = "Choose type of sorting:";
    String ENTER_ANOTHER_VALUE_TO_RETURN = "Enter another value to return";
    String NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING = "No available literature item IN shelf for sorting";

    List<String> parameterList();

    List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    List<MenuItemForSorting> getSortingMenuList();

    default void printSortingMenu(PrintWriter printWriter) {
        myCustomPrintln(printWriter, CHOOSE_TYPE_OF_SORTING);
        for (MenuItemForSorting menuItemForSorting : getSortingMenuList()) {
            printWriter.println(menuItemForSorting);
        }
        printWriter.println(ENTER_ANOTHER_VALUE_TO_RETURN);
    }

    static void myCustomPrintln(PrintWriter printWriter, String str) {
        printWriter.println(str);
    }

    default List<T> clarificationForSortingItems(List<T> items, int userChoice, PrintWriter printWriter) {
        if (items.isEmpty()) {
            printWriter.println(NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING);
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, items);
        }
        return items;
    }

    T getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter);

    T getRandomItem(Random random);

    Map<String, String> convertItemToListOfString(T item);

    String generateHTMLFormBodyToCreateItem();

    String generateHTMLFormBodyToCreateItem(Random random);

    boolean isValidHTMLFormData(Map<String, String> mapFieldValue);

    T generateItemByHTMLFormData(Map<String, String> mapFieldValue);

    default String generateSqlTableStr(Class<? extends Item> classType){
        List<String> parameterList = ItemHandlerProvider.getHandlerByClass(classType).parameterList();
        StringJoiner parametersJoiner = new StringJoiner(COMA_DELIMITER);
        parameterList.forEach(parametersJoiner::add);

        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(classType.getSimpleName()).append("s (\n");
        sb.append(classType.getSimpleName()).append("_id INTEGER PRIMARY KEY AUTOINCREMENT,\n");
        parameterList.forEach(o -> sb.append(o).append(" TEXT NOT NULL, \n"));
        sb.append("UNIQUE (").append(parametersJoiner).append(") ON CONFLICT IGNORE");
        sb.append(");");

        return sb.toString();
    }

    default String generateSqlInsertStr(T item){
        StringJoiner parametersJoiner = new StringJoiner(COMA_DELIMITER);
        List<String> stringList = ItemHandlerProvider.getHandlerByClass(item.getClass()).parameterList();
        stringList.forEach(parametersJoiner::add);
        StringJoiner valuesJoiner = new StringJoiner(COMA_DELIMITER);
        stringList.forEach(o -> valuesJoiner.add(VALUE_DELIMITER));

        return "INSERT OR IGNORE INTO " + item.getClass().getSimpleName() + "s" +
                "( " + parametersJoiner + " ) \n" +
                "VALUES ( " +valuesJoiner + " )";
    }
}
