package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingBooks;
import org.vshmaliukh.constants.enums_for_menu.MenuItem;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.services.ItemSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForBook;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.services.Utils.getRandomString;

public class BookHandler implements ItemHandler<Book> {

    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(Book::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);

    @Override
    public ConvertorToString getConvertorToString() {
        return new ConvertorToStringForBook();
    }

    @Override
    public List<Book> getSortedItems(int typeOfSorting, List<Book> inputList) {
        ItemSorterHandler<Book> bookItemSorterHandler = new ItemSorterHandler<>(inputList);
        for (MenuItem menuItem : MenuForSortingBooks.menuForSortingBooksItems) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(bookItemSorterHandler.getSortedLiterature(menuItem.getComparator()));
            }// TODO is it normal to save comparator in menu item
        }
        return Collections.emptyList();
    }

    @Override
    public void printSortingMenu(PrintWriter printWriter) {
        MenuForSortingBooks.printMenu(printWriter);
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
}
