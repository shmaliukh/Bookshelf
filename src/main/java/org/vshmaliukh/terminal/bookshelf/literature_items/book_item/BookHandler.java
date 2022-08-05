package org.vshmaliukh.terminal.bookshelf.literature_items.book_item;

import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.vshmaliukh.terminal.ConsoleTerminal.DATE_FORMAT_STR;
import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.terminal.services.Utils.getRandomString;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;

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
    public Book getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String author = inputHandlerForLiterature.getUserLiteratureAuthor();
        Date dateOfIssue = inputHandlerForLiterature.getUserLiteratureDateOfIssue();
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
        map.put(ItemTitles.NAME, book.getName());
        map.put(ItemTitles.PAGES, String.valueOf(book.getPagesNumber()));
        map.put(ItemTitles.BORROWED, String.valueOf(book.isBorrowed()));
        map.put(ItemTitles.AUTHOR, book.getAuthor());
        map.put(ItemTitles.DATE, new SimpleDateFormat(DATE_FORMAT_STR).format(book.getIssuanceDate()));
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                Utils.generateHTMLFormItem(NAME) +
                Utils.generateHTMLFormItem(PAGES) +
                Utils.generateHTMLFormItem(BORROWED) +
                Utils.generateHTMLFormItem(AUTHOR) +
                Utils.generateHTMLFormItem(DATE) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        return "" +
                Utils.generateHTMLFormItem(NAME, getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(PAGES, String.valueOf(random.nextInt(1000))) +
                Utils.generateHTMLFormItem(BORROWED, "n") +
                Utils.generateHTMLFormItem(AUTHOR, getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(DATE, new SimpleDateFormat(DATE_FORMAT_STR).format(new Date())) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String authorParameter = mapFieldValue.get(AUTHOR);
        String dateParameter = mapFieldValue.get(DATE);

        if (isValidBookInput(nameParameter, pagesParameter, borrowedParameter, authorParameter, dateParameter)) {
            return true;
        }
        return false;
    }

    public boolean isValidBookInput(String name, String pages, String borrowed, String author, String date) {
        return InputHandlerForLiterature.isValidInputString(name, PATTERN_FOR_NAME) &&
                InputHandlerForLiterature.isValidInputInteger(pages, PATTERN_FOR_PAGES) &&
                InputHandlerForLiterature.isValidInputString(borrowed, PATTERN_FOR_IS_BORROWED) &&
                InputHandlerForLiterature.isValidInputDate(date, new SimpleDateFormat(DATE_FORMAT_FOR_INPUT_HANDLER)) &&
                InputHandlerForLiterature.isValidInputString(author, PATTERN_FOR_AUTHOR);
    }

    @Override
    public Book generateItemByHTMLFormData(Map<String, String> mapFieldValue, PrintWriter printWriter) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String authorParameter = mapFieldValue.get(AUTHOR);
        String dateParameter = mapFieldValue.get(DATE);

        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(null, printWriter);
        String join = String.join(System.lineSeparator(), nameParameter, pagesParameter, borrowedParameter, authorParameter, dateParameter);
        inputHandlerForLiterature.scanner = new Scanner(join);

        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String author = inputHandlerForLiterature.getUserLiteratureAuthor();
        Date date = inputHandlerForLiterature.getUserLiteratureDateOfIssue();

        return new Book(name, pages, isBorrowed, author, date);
    }
}
