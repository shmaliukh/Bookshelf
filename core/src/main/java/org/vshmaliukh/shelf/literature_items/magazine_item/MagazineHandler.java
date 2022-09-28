package org.vshmaliukh.shelf.literature_items.magazine_item;

import org.vshmaliukh.services.file_service.sql_handler.AbleToHandleUserTableSql;
import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.services.input_handler.WebInputHandler;
import org.vshmaliukh.services.input_services.AbstractInputHandler;
import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.shelf.literature_items.ItemUtils;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;

public class MagazineHandler extends ItemHandler<Magazine> {

    public static final String MAGAZINE_TABLE_TITLE = Magazine.class.getSimpleName() + "s";

    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_PAGES = Comparator.comparing(Magazine::getPagesNumber);
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(Magazine::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public List<Magazine> getSortedItems(int typeOfSorting, List<Magazine> inputList) {
        for (MenuItemForSorting<Magazine> menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(ItemUtils.getSortedLiterature(inputList, menuItem.getComparator()));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemForSorting<Magazine>> getSortingMenuList() {
        return Collections.unmodifiableList(Arrays.asList(
                new MenuItemForSorting<Magazine>(1, "Sort by 'name'", MAGAZINE_COMPARATOR_BY_NAME),
                new MenuItemForSorting<Magazine>(2, "Sort by 'pages'", MAGAZINE_COMPARATOR_BY_PAGES)
        ));
    }

    @Override
    public Magazine getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter) {
        String name = consoleInputHandlerForLiterature.getUserLiteratureName();
        int pages = consoleInputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = consoleInputHandlerForLiterature.getUserLiteratureIsBorrowed();
        return new Magazine(name, pages, isBorrowed);
    }

    @Override
    public Magazine getRandomItem(Random random) {
        return new Magazine(
                ItemUtils.getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
    }

    @Override
    public Map<String, String> convertItemToListOfString(Magazine magazine) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, magazine.getClass().getSimpleName());
        map.put(ItemTitles.NAME, magazine.getName());
        map.put(ItemTitles.PAGES, String.valueOf(magazine.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(magazine.isBorrowed()));
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                ItemUtils.generateHTMLFormItem(ItemTitles.NAME, "text") +
                ItemUtils.generateHTMLFormItem(ItemTitles.PAGES, "number") +
                ItemUtils.generateHTMLFormRadio(ItemTitles.BORROWED) +
                "   <br>\n " +
                "   <input type = \"submit\" value = \"Submit\" />\n " +
                "   <br>\n " +
                "   <br>\n " +
                "</form>\n ";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        return "" +
                ItemUtils.generateHTMLFormItem(ItemTitles.NAME, "text", ItemUtils.getRandomString(random.nextInt(20), random)) +
                ItemUtils.generateHTMLFormItem(ItemTitles.PAGES, "number", String.valueOf(random.nextInt(1000))) +
                ItemUtils.generateHTMLFormRadio(ItemTitles.BORROWED) +
                "   <br>\n " +
                "   <input type = \"submit\" value = \"Submit\" />\n " +
                "   <br>\n " +
                "   <br>\n " +
                "</form>\n ";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(ItemTitles.NAME);
        String pagesParameter = mapFieldValue.get(ItemTitles.PAGES);
        String borrowedParameter = mapFieldValue.get(ItemTitles.BORROWED);

        return isValidUserParametersInput(nameParameter, pagesParameter, borrowedParameter);
    }

    public boolean isValidUserParametersInput(String name, String pages, String borrowed) {
        return AbstractInputHandler.isValidInputString(name, ConstantsForItemInputValidation.PATTERN_FOR_NAME) &&
                AbstractInputHandler.isValidInputInteger(pages, ConstantsForItemInputValidation.PATTERN_FOR_PAGES) &&
                AbstractInputHandler.isValidInputString(borrowed, ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
    }

    @Override
    public Magazine generateItemByParameterValueMap(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), ConstantsForItemInputValidation.PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);

        if (name != null && pages != null && isBorrowed != null) {
            return new Magazine(name, pages, isBorrowed);
        }
        return null;
    }

    // -------------------------------------------------------------------
    // SQL methods
    // -------------------------------------------------------------------

    @Override
    public Magazine readItemFromSqlDB(ResultSet rs) throws SQLException {
        return new Magazine(
                rs.getInt(ITEM_ID_SQL_PARAMETER),
                rs.getString(NAME_SQL_PARAMETER),
                rs.getInt(PAGES_SQL_PARAMETER),
                Boolean.parseBoolean(rs.getString(BORROWED_SQL_PARAMETER))
        );
    }

    @Override
    public String insertItemSqlLiteStr() {
        return " INSERT OR IGNORE INTO " + MAGAZINE_TABLE_TITLE +
                " ( " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " ) " +
                " VALUES(?,?,?,?)";
    }

    @Override
    public String insertItemMySqlStr() {
        return " INSERT IGNORE INTO " + sqlItemTableTitle() + " ( " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " ) " +
                " VALUES(?,?,?,?)";
    }

    @Override
    public String selectItemSqlStr() {
        return " SELECT " +
                ITEM_ID_SQL_PARAMETER + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER +
                " FROM " + MAGAZINE_TABLE_TITLE +
                " WHERE " + AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + " = ? ";
    }

    @Override
    public void insertItemValuesToSqlDB(PreparedStatement pstmt, Magazine item, Integer userID) throws SQLException {
        pstmt.setInt(1, userID);
        pstmt.setString(2, item.getName());
        pstmt.setInt(3, item.getPagesNumber());
        pstmt.setString(4, String.valueOf(item.isBorrowed()));
        pstmt.executeUpdate();
    }

    public String createTableSqlLiteStr() {
        return CREATE_TABLE_IF_NOT_EXISTS + sqlItemTableTitle() + " ( \n " +
                ITEM_ID_SQL_PARAMETER + INTEGER_PRIMARY_KEY_AUTOINCREMENT + " , \n " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + INTEGER_NOT_NULL + " , \n " +
                NAME_SQL_PARAMETER + TEXT_NOT_NULL + " , \n " +
                PAGES_SQL_PARAMETER + INTEGER_NOT_NULL + " , \n " +
                BORROWED_SQL_PARAMETER + TEXT_NOT_NULL + " , \n " +
                UNIQUE + " ( \n " +
                NAME_SQL_PARAMETER + " , \n " +
                PAGES_SQL_PARAMETER + " , \n " +
                BORROWED_SQL_PARAMETER + " \n " +
                " ) \n " +
                ON_CONFLICT_IGNORE +
                " ); ";
    }

    @Override
    public String createTableMySqlStr() {
        return CREATE_TABLE_IF_NOT_EXISTS + sqlItemTableTitle() + " ( \n " +
                ITEM_ID_SQL_PARAMETER + INT_AUTO_INCREMENT + " , \n " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + INT_NOT_NULL + " , \n " +
                NAME_SQL_PARAMETER + VARCHAR_200_NOT_NULL + " , \n " +
                PAGES_SQL_PARAMETER + INT_NOT_NULL + " , \n " +
                BORROWED_SQL_PARAMETER + VARCHAR_10_NOT_NULL + " , \n " +
                PRIMARY_KEY + ITEM_ID_SQL_PARAMETER + " ), \n " +
                CONSTRAINT_UC + sqlItemTableTitle() +
                UNIQUE + " ( \n " +
                NAME_SQL_PARAMETER + " , \n " +
                PAGES_SQL_PARAMETER + " , \n " +
                BORROWED_SQL_PARAMETER + " \n " +
                " ) \n " +
                ");";
    }

    @Override
    public String sqlItemTableTitle() {
        return MAGAZINE_TABLE_TITLE;
    }
}
