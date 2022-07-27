package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.menus.MenuItemForSorting;
import org.vshmaliukh.services.ItemSorterService;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.Terminal.DATE_FORMAT;
import static org.vshmaliukh.services.Utils.getRandomString;
import static org.vshmaliukh.handlers.ItemTitles.*;
import static org.vshmaliukh.handlers.ItemTitles.DATE;

public class BookHandler implements ItemHandler<Book> {

    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(Book::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);

    @Override
    public List<Book> getSortedItems(int typeOfSorting, List<Book> inputList) {
        ItemSorterService<Book> bookItemSorterService = new ItemSorterService<>(inputList);
        for (MenuItemForSorting menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(bookItemSorterService.getSortedLiterature(menuItem.getComparator()));
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
    public List<Book> clarificationForSortingItems(List<Book> items, int userChoice, PrintWriter printWriter) {
        if (items.isEmpty()) {
            printWriter.println("No available books IN shelf for sorting");
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, items);
        }
        return items;
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
                getRandomString(random.nextInt(10), random),
                new Date(random.nextLong()));
    }

    @Override
    public Map<String, String> convertItemToListOfString(Book book) {
        Map<String, String> map = new HashMap<>();
        map.put(TYPE, book.getClass().getSimpleName());
        map.put(NAME, book.getName());
        map.put(PAGES, String.valueOf(book.getPagesNumber()));
        map.put(BORROWED, String.valueOf(book.isBorrowed()));
        map.put(AUTHOR, book.getAuthor());
        map.put(DATE, DATE_FORMAT.format(book.getIssuanceDate()));
        return new HashMap<>(map);
    }
}
