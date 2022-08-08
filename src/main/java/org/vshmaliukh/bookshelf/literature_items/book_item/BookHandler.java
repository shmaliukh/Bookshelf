package org.vshmaliukh.bookshelf.literature_items.book_item;

import org.vshmaliukh.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal.services.Utils;
import org.vshmaliukh.console_terminal.services.input_services.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.console_terminal.services.input_services.WebInputHandler;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.vshmaliukh.bookshelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.console_terminal.ConsoleShelfHandler.DATE_FORMAT_STR;
import static org.vshmaliukh.console_terminal.services.Utils.getRandomString;
import static org.vshmaliukh.console_terminal.services.input_services.ConstantsForConsoleUserInputHandler.*;
import static org.vshmaliukh.console_terminal.services.input_services.InputHandler.*;
import static org.vshmaliukh.web.WebUtils.DATE_FORMAT_WEB_STR;

public class BookHandler implements ItemHandler<Book> {

    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(Book::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);

    @Override
    public List<Book> getSortedItems(int typeOfSorting, List<Book> inputList) {
        for (MenuItemForSorting menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(Utils.getSortedLiterature(inputList, menuItem.getComparator()));
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
        map.put(BORROWED, String.valueOf(book.isBorrowed()));
        map.put(ItemTitles.AUTHOR, book.getAuthor());
        map.put(ItemTitles.DATE, new SimpleDateFormat(DATE_FORMAT_STR).format(book.getIssuanceDate()));
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                Utils.generateHTMLFormItem(NAME, "text") +
                Utils.generateHTMLFormItem(PAGES, "number") +
                Utils.generateHTMLFormRadio(BORROWED) +
                Utils.generateHTMLFormItem(ItemTitles.AUTHOR, "text") +
                Utils.generateHTMLFormItem(ItemTitles.DATE, "date") +
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        String defaultDate = new SimpleDateFormat(DATE_FORMAT_WEB_STR).format(new Date());
        return "" +
                Utils.generateHTMLFormItem(NAME, "text", getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(PAGES, "number", String.valueOf(random.nextInt(1000))) +
                Utils.generateHTMLFormRadio(BORROWED) +
                Utils.generateHTMLFormItem(ItemTitles.AUTHOR, "text", getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(ItemTitles.DATE, "date", defaultDate) +
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
        return isValidInputString(name, PATTERN_FOR_NAME) &&
                isValidInputInteger(pages, PATTERN_FOR_PAGES) &&
                isValidInputString(borrowed, PATTERN_FOR_IS_BORROWED) &&
                isValidInputDate(date, new SimpleDateFormat(DATE_FORMAT_WEB_STR)) &&
                isValidInputString(author, PATTERN_FOR_AUTHOR);
    }

    @Override
    public Book generateItemByHTMLFormData(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), PATTERN_FOR_IS_BORROWED);
        String author = webInputHandler.getUserString(mapFieldValue.get(AUTHOR), PATTERN_FOR_AUTHOR);
        Date date = webInputHandler.getUserDate(mapFieldValue.get(DATE), new SimpleDateFormat(DATE_FORMAT_WEB_STR));

        if (name != null && pages != null && isBorrowed != null && author != null && date != null) {
            return new Book(name, pages, isBorrowed, author, date);
        }
        return null;
    }
}
