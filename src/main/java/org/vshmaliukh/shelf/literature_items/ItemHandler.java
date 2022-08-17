package org.vshmaliukh.shelf.literature_items;

import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;

public interface ItemHandler<T extends Item> {
    String ITEM_ID_SQL_PARAMETER = "id";
    String NAME_SQL_PARAMETER = NAME.toLowerCase();
    String PAGES_SQL_PARAMETER = PAGES.toLowerCase();
    String BORROWED_SQL_PARAMETER = BORROWED.toLowerCase();
    String AUTHOR_SQL_PARAMETER = AUTHOR.toLowerCase();
    String DATE_SQL_PARAMETER = DATE.toLowerCase();
    String PUBLISHER_SQL_PARAMETER = PUBLISHER.toLowerCase();

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

    String insertItemSqlLiteStr();

    String insertItemMySqlStr();

    String selectItemSqlStr();

    void insertItemValues(PreparedStatement pstmt, T item, Integer userID) throws SQLException;

    T readItemFromSql(ResultSet rs) throws SQLException;

    String generateSqlLiteTableStr();

    String generateMySqlTableStr();

    default String deleteItemFromDBStr() {
        return "" +
                " DELETE FROM " +
                getSqlTableTitle() +
                " WHERE " + ITEM_ID_SQL_PARAMETER + " = ? ";
    }

    default String changeItemBorrowedStateInDBStr() {
        return "" +
                " UPDATE " +
                getSqlTableTitle() +
                " SET " + BORROWED + " = ? " +
                " WHERE " + ITEM_ID_SQL_PARAMETER + " = ? ";
    }

    String getSqlTableTitle(); // TODO rename
}
