package org.vshmaliukh.shelf.literature_items;

import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.vshmaliukh.console_terminal_app.ConsoleUI.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;

public abstract class ItemHandler<T extends Item>  implements SqlStatementInterface {

    protected static List<String> parameterList = Collections.unmodifiableList(Arrays.asList(NAME, PAGES, BORROWED));

    public abstract List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    public abstract List<MenuItemForSorting> getSortingMenuList();

    public void printSortingMenu(PrintWriter printWriter) {
        myCustomPrintln(printWriter, CHOOSE_TYPE_OF_SORTING);
        for (MenuItemForSorting menuItemForSorting : getSortingMenuList()) {
            printWriter.println(menuItemForSorting);
        }
        printWriter.println(ENTER_ANOTHER_VALUE_TO_RETURN);
    }

    static void myCustomPrintln(PrintWriter printWriter, String str) {
        printWriter.println(str);
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

    public abstract String generateHTMLFormBodyToCreateItem();

    public abstract String generateHTMLFormBodyToCreateItem(Random random);

    public abstract boolean isValidHTMLFormData(Map<String, String> mapFieldValue);

    public abstract T generateItemByParameterValueMap(Map<String, String> mapFieldValue);

    public abstract String insertItemSqlLiteStr();

    public abstract String insertItemMySqlStr();

    public abstract String selectItemSqlStr();

    public abstract void insertItemValues(PreparedStatement pstmt, T item, Integer userID) throws SQLException;

    public abstract T readItemFromSql(ResultSet rs) throws SQLException;

    public abstract String generateSqlLiteTableStr();

    public abstract String generateMySqlTableStr();

}
