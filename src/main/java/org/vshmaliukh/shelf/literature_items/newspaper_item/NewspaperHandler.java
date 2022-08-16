package org.vshmaliukh.shelf.literature_items.newspaper_item;

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

import static org.vshmaliukh.services.file_service.sqllite.SqlLiteHandler.USER_ID;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.isValidInputInteger;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.isValidInputString;

public class NewspaperHandler implements ItemHandler<Newspaper> {

    public static final String NEWSPAPER_TABLE_TITLE = Newspaper.class.getSimpleName() + "s";

    public List<String> parameterList() {
        return parameterList;
    }

    public static final Comparator<Newspaper> NEWSPAPER_COMPARATOR_BY_PAGES = Comparator.comparing(Newspaper::getPagesNumber);
    public static final Comparator<Newspaper> NEWSPAPER_COMPARATOR_BY_NAME = Comparator.comparing(Newspaper::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public List<Newspaper> getSortedItems(int typeOfSorting, List<Newspaper> inputList) {
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
                new MenuItemForSorting(1, "Sort by 'name' value", NEWSPAPER_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'pages' value", NEWSPAPER_COMPARATOR_BY_PAGES)
        ));
    }

    @Override
    public Newspaper getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter) {
        String name = consoleInputHandlerForLiterature.getUserLiteratureName();
        int pages = consoleInputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = consoleInputHandlerForLiterature.getUserLiteratureIsBorrowed();
        return new Newspaper(name, pages, isBorrowed);
    }

    @Override
    public Newspaper getRandomItem(Random random) {
        return new Newspaper(
                ItemUtils.getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
    }

    @Override
    public Map<String, String> convertItemToListOfString(Newspaper newspaper) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, newspaper.getClass().getSimpleName());
        map.put(ItemTitles.NAME, newspaper.getName());
        map.put(ItemTitles.PAGES, String.valueOf(newspaper.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(newspaper.isBorrowed()));
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                ItemUtils.generateHTMLFormItem(ItemTitles.NAME, "text") +
                ItemUtils.generateHTMLFormItem(ItemTitles.PAGES, "number") +
                ItemUtils.generateHTMLFormRadio(ItemTitles.BORROWED) +
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
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(ItemTitles.NAME);
        String pagesParameter = mapFieldValue.get(ItemTitles.PAGES);
        String borrowedParameter = mapFieldValue.get(ItemTitles.BORROWED);

        return isValidUserParametersInput(nameParameter, pagesParameter, borrowedParameter);
    }

    public boolean isValidUserParametersInput(String name, String pages, String borrowed) {
        return isValidInputString(name, ConstantsForItemInputValidation.PATTERN_FOR_NAME) &&
                isValidInputInteger(pages, ConstantsForItemInputValidation.PATTERN_FOR_PAGES) &&
                isValidInputString(borrowed, ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
    }

    @Override
    public Newspaper generateItemByParameterValueMap(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), ConstantsForItemInputValidation.PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);

        if (name != null && pages != null && isBorrowed != null) {
            return new Newspaper(name, pages, isBorrowed);
        }
        return null;
    }

    @Override
    public Newspaper readItemFromSql(ResultSet rs) throws SQLException {
        return new Newspaper(
                rs.getInt(ID),
                rs.getString(NAME),
                rs.getInt(PAGES),
                Boolean.parseBoolean(rs.getString(BORROWED))
        );
    }

    @Override
    public String insertItemSqlStr() {
        return " INSERT INTO " + NEWSPAPER_TABLE_TITLE +
                " ( " +
                USER_ID + " , " +
                NAME + " , " +
                PAGES + " , " +
                BORROWED + " ) " +
                " VALUES(?,?,?,?)";
    }

    @Override
    public String selectItemSqlStr(Integer userId) {
        return " SELECT " +
                ID + " , " +
                NAME + " , " +
                PAGES + " , " +
                BORROWED +
                " FROM " + NEWSPAPER_TABLE_TITLE +
                " WHERE " + USER_ID + " = " + userId + ";";
    }

    @Override
    public void insertItemValues(PreparedStatement pstmt, Newspaper item, Integer userID) throws SQLException {
        pstmt.setInt(1, userID);
        pstmt.setString(2, item.getName());
        pstmt.setInt(3, item.getPagesNumber());
        pstmt.setString(4, String.valueOf(item.isBorrowed()));
        pstmt.executeUpdate();
    }

    public String generateSqlTableStr() {
        return "CREATE TABLE IF NOT EXISTS " + NEWSPAPER_TABLE_TITLE +
                "(\n" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                USER_ID + " INTEGER NOT NULL, \n" +
                NAME + " TEXT NOT NULL, \n" +
                PAGES + " INTEGER NOT NULL, \n" +
                BORROWED + " TEXT NOT NULL \n" +
                ");";
    }

    @Override
    public String deleteItemFromDBStr(Integer id) {
        return "" +
                " DELETE FROM " +
                NEWSPAPER_TABLE_TITLE +
                " WHERE id = ?";
    }
}
