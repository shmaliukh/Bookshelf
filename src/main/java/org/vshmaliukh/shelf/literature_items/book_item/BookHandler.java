package org.vshmaliukh.shelf.literature_items.book_item;

import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;
import org.vshmaliukh.shelf.literature_items.*;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.tomcat_web_app.WebInputHandler;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.vshmaliukh.services.file_service.sqllite.SqlLiteHandler.USER_ID;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.*;
import static org.vshmaliukh.shelf.literature_items.ItemUtils.*;
import static org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler.DATE_FORMAT_STR;

public class BookHandler implements ItemHandler<Book> {

    public static final String BOOK_TABLE_TITLE = Book.class.getSimpleName() + "s";

    public List<String> parameterList() {
        List<String> parameterList = new ArrayList<>(ItemHandler.parameterList);
        parameterList.add(AUTHOR);
        parameterList.add(DATE);
        return Collections.unmodifiableList(parameterList);
    }

    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(Book::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);

    @Override
    public List<Book> getSortedItems(int typeOfSorting, List<Book> inputList) {
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
                new MenuItemForSorting(1, "Sort by 'name' value", BOOK_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'author' value", BOOK_COMPARATOR_BY_AUTHOR),
                new MenuItemForSorting(3, "Sort by 'page number' value", BOOK_COMPARATOR_BY_PAGES),
                new MenuItemForSorting(4, "Sort by 'date' value", BOOK_COMPARATOR_BY_DATE)
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
    public Map<String, String> convertItemToListOfString(Book book) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, book.getClass().getSimpleName());
        map.put(NAME, book.getName());
        map.put(PAGES, String.valueOf(book.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(book.isBorrowed()));
        map.put(ItemTitles.AUTHOR, book.getAuthor());
        map.put(ItemTitles.DATE, new SimpleDateFormat(DATE_FORMAT_STR).format(book.getIssuanceDate()));
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
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        String defaultDate = new SimpleDateFormat(DATE_FORMAT_STR).format(new Date());
        return "" +
                ItemUtils.generateHTMLFormItem(NAME, "text", getRandomString(random.nextInt(20), random)) +
                ItemUtils.generateHTMLFormItem(PAGES, "number", String.valueOf(random.nextInt(1000))) +
                ItemUtils.generateHTMLFormRadio(BORROWED) +
                ItemUtils.generateHTMLFormItem(ItemTitles.AUTHOR, "text", getRandomString(random.nextInt(20), random)) +
                ItemUtils.generateHTMLFormItem(ItemTitles.DATE, "date", defaultDate) +
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
        String authorParameter = mapFieldValue.get(ItemTitles.AUTHOR);
        String dateParameter = mapFieldValue.get(ItemTitles.DATE);

        return isValidUserParametersInput(nameParameter, pagesParameter, borrowedParameter, authorParameter, dateParameter);
    }

    public boolean isValidUserParametersInput(String name, String pages, String borrowed, String author, String date) {
        return isValidInputString(name, ConstantsForItemInputValidation.PATTERN_FOR_NAME) &&
                isValidInputInteger(pages, ConstantsForItemInputValidation.PATTERN_FOR_PAGES) &&
                isValidInputString(borrowed, ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED) &&
                isValidInputDate(date, new SimpleDateFormat(DATE_FORMAT_STR)) &&
                isValidInputString(author, ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR);
    }

    @Override
    public Book generateItemByParameterValueMap(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), ConstantsForItemInputValidation.PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
        String author = webInputHandler.getUserString(mapFieldValue.get(AUTHOR), ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR);
        Date date = webInputHandler.getUserDate(mapFieldValue.get(DATE), new SimpleDateFormat(DATE_FORMAT_STR));

        if (name != null && pages != null && isBorrowed != null && author != null && date != null) {
            return new Book(name, pages, isBorrowed, author, date);
        }
        return null;
    }

    // -------------------------------------------------------------------
    // SQLlite methods
    // -------------------------------------------------------------------

    @Override
    public String insertItemSqlStr() {
        return " INSERT INTO " + BOOK_TABLE_TITLE +
                " ( " +
                USER_ID + " , " +
                NAME + " , " +
                PAGES + " , " +
                BORROWED + " , " +
                AUTHOR + " , " +
                DATE + " ) " +
                " VALUES(?,?,?,?,?,?)";
    }

    @Override
    public String selectItemSqlStr(Integer userId) {
        return " SELECT " +
                NAME + " , " +
                PAGES + " , " +
                BORROWED + " , " +
                AUTHOR + " , " +
                DATE +
                " FROM " + BOOK_TABLE_TITLE + " " +
                " WHERE " + USER_ID  + " = " + userId + ";"; // todo use PrepareStatement
    }

    @Override
    public void insertItemValues(PreparedStatement pstmt, Book item, Integer userID) throws SQLException {
        pstmt.setInt(1, userID);
        pstmt.setString(2, item.getName());
        pstmt.setInt(3, item.getPagesNumber());
        pstmt.setString(4, String.valueOf(item.isBorrowed()));
        pstmt.setString(5, item.getAuthor());
        pstmt.setString(6, new SimpleDateFormat(DATE_FORMAT_STR).format(item.getIssuanceDate()));
        pstmt.executeUpdate();
    }

    @Override
    public Book readItemFromSql(ResultSet rs) throws SQLException {
        Date issuanceDate;
        try {
            issuanceDate = new SimpleDateFormat(DATE_FORMAT_STR).parse(rs.getString(DATE));
        } catch (ParseException e) {
            issuanceDate = null;
        }
        return new Book(
                rs.getString(NAME),
                rs.getInt(PAGES),
                Boolean.parseBoolean(rs.getString(BORROWED)),
                rs.getString(AUTHOR),
                issuanceDate
        );
    }

    @Override
    public String generateSqlTableStr() {
        return "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE_TITLE +
                "(\n" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT , \n" +
                USER_ID + " INTEGER NOT NULL, \n" +
                NAME + " TEXT NOT NULL, \n" +
                PAGES + " INTEGER NOT NULL, \n" +
                BORROWED + " TEXT NOT NULL, \n" +
                AUTHOR + " TEXT NOT NULL, \n" +
                DATE + " TEXT NOT NULL \n" +
                //"UNIQUE ("
                ");";
    }

}
