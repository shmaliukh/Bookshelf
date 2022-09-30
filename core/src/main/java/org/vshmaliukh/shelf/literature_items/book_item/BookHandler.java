package org.vshmaliukh.shelf.literature_items.book_item;

import org.vshmaliukh.ConfigFile;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.shelf.literature_items.ItemUtils.getRandomString;

public class BookHandler extends ItemHandler<Book> {

    public static final String BOOK_TABLE_TITLE = Book.class.getSimpleName() + "s";

    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(Book::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);

    @Override
    public List<Book> getSortedItems(int typeOfSorting, List<Book> inputList) {
        for (MenuItemForSorting<Book> menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(ItemUtils.getSortedLiterature(inputList, menuItem.getComparator()));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemForSorting<Book>> getSortingMenuList() {
        return Collections.unmodifiableList(Arrays.asList(
                new MenuItemForSorting<>(1, "Sort by 'name'", BOOK_COMPARATOR_BY_NAME),
                new MenuItemForSorting<>(2, "Sort by 'author'", BOOK_COMPARATOR_BY_AUTHOR),
                new MenuItemForSorting<>(3, "Sort by 'page number'", BOOK_COMPARATOR_BY_PAGES),
                new MenuItemForSorting<>(4, "Sort by 'date'", BOOK_COMPARATOR_BY_DATE)
        ));
    }

    @Override
    public Book getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter) {
        String name = consoleInputHandlerForLiterature.getUserLiteratureName();
        int pages = consoleInputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = consoleInputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String author = consoleInputHandlerForLiterature.getUserLiteratureAuthor();
        Date dateOfIssue = consoleInputHandlerForLiterature.getUserLiteratureDateOfIssue();
        return new Book(name, pages, isBorrowed, author, dateOfIssue);
    }

    @Override
    public Book getRandomItem(Random random) {
        return new Book(
                getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false,
                getRandomString(random.nextInt(20), random),
                new Date(random.nextInt(2500)));
    }

    @Override
    public Map<String, String> convertItemToMapOfString(Book book) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, book.getClass().getSimpleName());
        map.put(NAME, book.getName());
        map.put(PAGES, String.valueOf(book.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(book.isBorrowed()));
        map.put(ItemTitles.AUTHOR, book.getAuthor());
        map.put(ItemTitles.DATE, new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).format(book.getIssuanceDate()));
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                ItemUtils.generateHTMLFormItem(NAME, "text") +
                ItemUtils.generateHTMLFormItem(PAGES, "number") +
                ItemUtils.generateHTMLFormRadio(BORROWED) +
                ItemUtils.generateHTMLFormItem(ItemTitles.AUTHOR, "text") +
                ItemUtils.generateHTMLFormItem(ItemTitles.DATE, "date") +
                "   <br>\n " +
                "   <input type = \"submit\" value = \"Submit\" />\n " +
                "   <br>\n " +
                "   <br>\n " +
                "</form>\n ";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        String defaultDate = new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).format(new Date());
        return "" +
                ItemUtils.generateHTMLFormItem(NAME, "text", getRandomString(random.nextInt(20), random)) +
                ItemUtils.generateHTMLFormItem(PAGES, "number", String.valueOf(random.nextInt(1000))) +
                ItemUtils.generateHTMLFormRadio(BORROWED) +
                ItemUtils.generateHTMLFormItem(ItemTitles.AUTHOR, "text", getRandomString(random.nextInt(20), random)) +
                ItemUtils.generateHTMLFormItem(ItemTitles.DATE, "date", defaultDate) +
                "   <br>\n " +
                "   <input type = \"submit\" value = \"Submit\" />\n " +
                "   <br>\n " +
                "   <br>\n " +
                "</form>\n ";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String authorParameter = mapFieldValue.get(ItemTitles.AUTHOR);
        String dateParameter = mapFieldValue.get(ItemTitles.DATE);

        return isValidUserParametersInput(nameParameter, pagesParameter, borrowedParameter, authorParameter, dateParameter);
    }

    public boolean isValidUserParametersInput(String name, String pages, String borrowed, String author, String date) {
        return AbstractInputHandler.isValidInputString(name, ConstantsForItemInputValidation.PATTERN_FOR_NAME) &&
                AbstractInputHandler.isValidInputInteger(pages, ConstantsForItemInputValidation.PATTERN_FOR_PAGES) &&
                AbstractInputHandler.isValidInputString(borrowed, ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED) &&
                AbstractInputHandler.isValidInputDate(date, new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR)) &&
                AbstractInputHandler.isValidInputString(author, ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR);
    }

    @Override
    public Book generateItemByParameterValueMap(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), ConstantsForItemInputValidation.PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
        String author = webInputHandler.getUserString(mapFieldValue.get(AUTHOR), ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR);
        Date date = webInputHandler.getUserDate(mapFieldValue.get(DATE), new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR));

        if (name != null && pages != null && isBorrowed != null && author != null && date != null) {
            return new Book(name, pages, isBorrowed, author, date);
        }
        return null;
    }

    // -------------------------------------------------------------------
    // SQL methods
    // -------------------------------------------------------------------

    @Override
    public String insertItemSqlLiteStr() {
        return " INSERT OR IGNORE INTO " + BOOK_TABLE_TITLE +
                " ( " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " , " +
                AUTHOR_SQL_PARAMETER + " , " +
                DATE_SQL_PARAMETER + " ) " +
                " VALUES(?,?,?,?,?,?)";
    }

    @Override
    public String insertItemMySqlStr() {
        return " INSERT " +
                //"OR
                "IGNORE" +
                " INTO " + BOOK_TABLE_TITLE +
                " ( " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " , " +
                AUTHOR_SQL_PARAMETER + " , " +
                DATE_SQL_PARAMETER + " ) " +
                " VALUES(?,?,?,?,?,?)";
    }

    @Override
    public String selectItemSqlStr() {
        return " SELECT " +
                ITEM_ID_SQL_PARAMETER + " , " +
                NAME_SQL_PARAMETER + " , " +
                PAGES_SQL_PARAMETER + " , " +
                BORROWED_SQL_PARAMETER + " , " +
                AUTHOR_SQL_PARAMETER + " , " +
                DATE_SQL_PARAMETER +
                " FROM " + BOOK_TABLE_TITLE + " " +
                " WHERE " + AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + " = ? ";
    }

    @Override
    public void insertItemValuesToSqlDB(PreparedStatement preparedStatement, Book item, Integer userID) throws SQLException {
        preparedStatement.setInt(1, userID);
        preparedStatement.setString(2, item.getName());
        preparedStatement.setInt(3, item.getPagesNumber());
        preparedStatement.setString(4, String.valueOf(item.isBorrowed()));
        preparedStatement.setString(5, item.getAuthor());
        preparedStatement.setString(6, new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).format(item.getIssuanceDate()));
        preparedStatement.executeUpdate();
    }

    @Override
    public Book readItemFromSqlDB(ResultSet rs) throws SQLException {
        Date issuanceDate;
        try {
            issuanceDate = new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse(rs.getString(DATE_SQL_PARAMETER));
        } catch (ParseException e) {
            issuanceDate = new Date(); // TODO
        }
        return new Book(
                rs.getInt(ITEM_ID_SQL_PARAMETER),
                rs.getString(NAME_SQL_PARAMETER),
                rs.getInt(PAGES_SQL_PARAMETER),
                Boolean.parseBoolean(rs.getString(BORROWED_SQL_PARAMETER)),
                rs.getString(AUTHOR_SQL_PARAMETER),
                issuanceDate
        );
    }

    @Override
    public String createTableSqlLiteStr() {
        return CREATE_TABLE_IF_NOT_EXISTS + sqlItemTableTitle() + " ( \n " +
                ITEM_ID_SQL_PARAMETER + INTEGER_PRIMARY_KEY_AUTOINCREMENT + " , \n " +
                AbleToHandleUserTableSql.USER_ID_SQL_PARAMETER_FOR_ANOTHER_TABLES + INTEGER_NOT_NULL + " , \n " +
                NAME_SQL_PARAMETER + TEXT_NOT_NULL + " , \n " +
                PAGES_SQL_PARAMETER + INTEGER_NOT_NULL + " , \n " +
                BORROWED_SQL_PARAMETER + TEXT_NOT_NULL + " , \n " +
                AUTHOR_SQL_PARAMETER + TEXT_NOT_NULL + " , \n " +
                DATE_SQL_PARAMETER + TEXT_NOT_NULL + " , \n " +
                UNIQUE + " ( \n " +
                NAME_SQL_PARAMETER + " , \n " +
                PAGES_SQL_PARAMETER + " , \n " +
                BORROWED_SQL_PARAMETER + " , \n " +
                AUTHOR_SQL_PARAMETER + " , \n " +
                DATE_SQL_PARAMETER + " \n " +
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
                AUTHOR_SQL_PARAMETER + VARCHAR_200_NOT_NULL + " , \n " +
                DATE_SQL_PARAMETER + VARCHAR_25_NOT_NULL + " , \n " +
                PRIMARY_KEY + ITEM_ID_SQL_PARAMETER + " ), \n " +
                CONSTRAINT_UC + sqlItemTableTitle() +
                UNIQUE + " ( \n " +
                NAME_SQL_PARAMETER + " , \n " +
                PAGES_SQL_PARAMETER + " , \n " +
                BORROWED_SQL_PARAMETER + " , \n " +
                AUTHOR_SQL_PARAMETER + " , \n " +
                DATE_SQL_PARAMETER + " \n " +
                " ) \n " +
                ");";
    }

    @Override
    public String sqlItemTableTitle() {
        return BOOK_TABLE_TITLE;
    }
}
