package org.vshmaliukh.shelf.literature_items.comics_item;

import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.shelf.literature_items.ItemUtils;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.tomcat_web_app.WebInputHandler;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.shelf.literature_items.ItemUtils.getRandomString;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.isValidInputInteger;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.isValidInputString;
import static org.vshmaliukh.shelf.shelf_handler.User.USER_ID_SQL_PARAMETER;

public class ComicsHandler implements ItemHandler<Comics> {

    public static final String COMICS_TABLE_TITLE = Comics.class.getSimpleName() + "s";

    public List<String> parameterList() {
        List<String> parameterList = new ArrayList<>(ItemHandler.parameterList);
        parameterList.add(PUBLISHER);
        return Collections.unmodifiableList(parameterList);
    }

    public static final Comparator<Comics> COMICS_COMPARATOR_BY_NAME = Comparator.comparing(Comics::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Comics> COMICS_COMPARATOR_BY_PUBLISHER = Comparator.comparing(Comics::getPublisher, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Comics> COMICS_COMPARATOR_BY_PAGES = Comparator.comparing(Comics::getPagesNumber);

    @Override
    public List<Comics> getSortedItems(int typeOfSorting, List<Comics> inputList) {
        for (MenuItemForSorting menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(ItemUtils.getSortedLiterature(inputList, menuItem.getComparator()));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemForSorting> getSortingMenuList() {
        return Collections.unmodifiableList(Arrays.asList(
                new MenuItemForSorting(1, "Sort by 'name' value", COMICS_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'page number' value", COMICS_COMPARATOR_BY_PAGES),
                new MenuItemForSorting(3, "Sort by 'publisher' value", COMICS_COMPARATOR_BY_PUBLISHER)
        ));
    }

    @Override
    public Comics getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter) {
        String name = consoleInputHandlerForLiterature.getUserLiteratureName();
        int pages = consoleInputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = consoleInputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String publisher = consoleInputHandlerForLiterature.getUserLiteraturePublisher();
        return new Comics(name, pages, isBorrowed, publisher);
    }

    @Override
    public Comics getRandomItem(Random random) {
        return new Comics(
                getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false,
                getRandomString(random.nextInt(20), random));
    }

    @Override
    public Map<String, String> convertItemToListOfString(Comics comics) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, comics.getClass().getSimpleName());
        map.put(ItemTitles.NAME, comics.getName());
        map.put(ItemTitles.PAGES, String.valueOf(comics.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(comics.isBorrowed()));
        map.put(ItemTitles.PUBLISHER, comics.getPublisher());
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                ItemUtils.generateHTMLFormItem(ItemTitles.NAME, "text") +
                ItemUtils.generateHTMLFormItem(ItemTitles.PAGES, "number") +
                ItemUtils.generateHTMLFormRadio(ItemTitles.BORROWED) +
                ItemUtils.generateHTMLFormItem(PUBLISHER, "text") +
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        return "" +
                ItemUtils.generateHTMLFormItem(ItemTitles.NAME, "text", ItemUtils.getRandomString(random.nextInt(20), random)) +
                ItemUtils.generateHTMLFormItem(ItemTitles.PAGES, "number", String.valueOf(random.nextInt(1000))) +
                ItemUtils.generateHTMLFormRadio(ItemTitles.BORROWED) +
                ItemUtils.generateHTMLFormItem(PUBLISHER, "text", getRandomString(random.nextInt(20), random)) +
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String publisherParameter = mapFieldValue.get(PUBLISHER);
        return isValidUserParametersInput(nameParameter, pagesParameter, borrowedParameter, publisherParameter);
    }

    public boolean isValidUserParametersInput(String name, String pages, String borrowed, String publisherParameter) {
        return isValidInputString(name, ConstantsForItemInputValidation.PATTERN_FOR_NAME) &&
                isValidInputInteger(pages, ConstantsForItemInputValidation.PATTERN_FOR_PAGES) &&
                isValidInputString(borrowed, ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED) &&
                isValidInputString(publisherParameter, ConstantsForItemInputValidation.PATTERN_FOR_PUBLISHER);
    }

    @Override
    public Comics generateItemByParameterValueMap(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), ConstantsForItemInputValidation.PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
        String publisher = webInputHandler.getUserString(mapFieldValue.get(PUBLISHER), ConstantsForItemInputValidation.PATTERN_FOR_PUBLISHER);

        if (name != null && pages != null && isBorrowed != null && publisher != null) {
            return new Comics(name, pages, isBorrowed, publisher);
        }
        return null;
    }

    // -------------------------------------------------------------------
    // SQLlite methods
    // -------------------------------------------------------------------

    @Override
    public Comics readItemFromSql(ResultSet rs) throws SQLException {
        return new Comics(
                rs.getInt(ITEM_ID_SQL_PARAMETER),
                rs.getString(NAME_SQL_PARAMETER),
                rs.getInt(PAGES_SQL_PARAMETER),
                Boolean.parseBoolean(rs.getString(BORROWED_SQL_PARAMETER)),
                rs.getString(PUBLISHER_SQL_PARAMETER)
        );
    }

    @Override
    public String insertItemSqlStr() {
        return " INSERT OR IGNORE INTO " + COMICS_TABLE_TITLE +
                " ( " +
                USER_ID_SQL_PARAMETER + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " , " +
                PUBLISHER_SQL_PARAMETER + " ) " +
                " VALUES(?,?,?,?,?)";
    }

    @Override
    public String selectItemSqlStr() {
        return " SELECT " +
                ITEM_ID_SQL_PARAMETER + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " , " +
                PUBLISHER_SQL_PARAMETER +
                " FROM " + COMICS_TABLE_TITLE  +
                " WHERE " + USER_ID_SQL_PARAMETER + " = ? ";
    }

    @Override
    public void insertItemValues(PreparedStatement pstmt, Comics item, Integer userID) throws SQLException {
        pstmt.setInt(1, userID);
        pstmt.setString(2, item.getName());
        pstmt.setInt(3, item.getPagesNumber());
        pstmt.setString(4, String.valueOf(item.isBorrowed()));
        pstmt.setString(5, item.getPublisher());
        pstmt.executeUpdate();
    }

    public String generateSqlTableStr() {
        return "CREATE TABLE IF NOT EXISTS " + COMICS_TABLE_TITLE +
                "(\n" +
                ITEM_ID_SQL_PARAMETER + " INTEGER PRIMARY KEY AUTOINCREMENT , \n" +
                USER_ID_SQL_PARAMETER + " INTEGER NOT NULL, \n" +
                NAME_SQL_PARAMETER + " TEXT NOT NULL, \n" +
                PAGES_SQL_PARAMETER + " INTEGER NOT NULL, \n" +
                BORROWED_SQL_PARAMETER + " TEXT NOT NULL, \n" +
                PUBLISHER_SQL_PARAMETER + " TEXT NOT NULL, \n" +
                " UNIQUE (" +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " , " +
                PUBLISHER_SQL_PARAMETER +
                " ) ON CONFLICT IGNORE \n" +
                ");";
    }

    @Override
    public String getSqlTableTitle() {
        return COMICS_TABLE_TITLE;
    }
}
