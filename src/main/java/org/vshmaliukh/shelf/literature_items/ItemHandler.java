package org.vshmaliukh.shelf.literature_items;

import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.shelf.literature_items.ItemUtils.COMA_DELIMITER;

public interface ItemHandler<T extends Item> {

    String ID = "id";

    List<String> parameterList = Collections.unmodifiableList(Arrays.asList(NAME, PAGES, BORROWED));

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

    T generateItemByParameterValueMap(Map<String, String> mapFieldValue);

    default <T extends Item> String generateSqlSelectAllParametersByClass(Class<T> classType) {
        List<String> parameterList = ItemHandlerProvider.getHandlerByClass(classType).parameterList();
        StringJoiner parametersJoiner = new StringJoiner(COMA_DELIMITER);
        parameterList.forEach(parametersJoiner::add);

        return " SELECT \n" +
                parametersJoiner +
                " FROM \n" +
                classType.getSimpleName() + "s";
    }

    String insertItemSqlStr();

    String selectItemSqlStr(Integer userId);

    void insertItemValues(PreparedStatement pstmt, T item, Integer userID) throws SQLException;

    T readItemFromSql(ResultSet rs) throws SQLException;

    String generateSqlTableStr();

    default String deleteItemFromDBStr() {
        return "" +
                " DELETE FROM " +
                getSqlTableTitle() +
                " WHERE " + ID + " = ? ";
    }

    default String changeItemBorrowedStateInDBStr() {
        return "" +
                " UPDATE " +
                getSqlTableTitle() +
                " SET " + BORROWED + " = ? " +
                " WHERE " + ID + " = ? ";
    }

    String getSqlTableTitle(); // TODO rename
}
