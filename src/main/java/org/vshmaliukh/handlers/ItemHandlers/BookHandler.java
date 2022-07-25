package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingBooks;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.services.ItemSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForBook;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.services.Utils.getRandomString;

public class BookHandler implements ItemHandler<Book> {

    private static final List<String> titleListForBooks = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED", "AUTHOR", "DATE"));
    public static final Comparator<Book> BOOK_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());
    public static final Comparator<Book> BOOK_COMPARATOR_BY_AUTHOR = Comparator.comparing(o -> o.getAuthor().toLowerCase());
    public static final Comparator<Book> BOOK_COMPARATOR_BY_PAGES = Comparator.comparing(Book::getPagesNumber);
    public static final Comparator<Book> BOOK_COMPARATOR_BY_DATE = Comparator.comparing(Book::getIssuanceDate);

    @Override
    public ConvertorToString getConvertorToString() {
        return new ConvertorToStringForBook();
    }

    @Override
    public List<Book> getSortedItems(int typeOfSorting, List<Book> inputList) {
        MenuForSortingBooks byIndex = MenuForSortingBooks.getByIndex(typeOfSorting);
        ItemSorterHandler<Book> bookItemSorterHandler = new ItemSorterHandler<>(inputList);
        switch (byIndex) {
            case SORT_BOOKS_BY_NAME:
                return new ArrayList<>(bookItemSorterHandler.getSortedLiterature(BOOK_COMPARATOR_BY_NAME));
            case SORT_BOOKS_BY_PAGES_NUMBER:
                return new ArrayList<>(bookItemSorterHandler.getSortedLiterature(BOOK_COMPARATOR_BY_PAGES));
            case SORT_BOOKS_BY_AUTHOR:
                return new ArrayList<>(bookItemSorterHandler.getSortedLiterature(BOOK_COMPARATOR_BY_AUTHOR));
            case SORT_BOOKS_BY_DATE_OF_ISSUE:
                return new ArrayList<>(bookItemSorterHandler.getSortedLiterature(BOOK_COMPARATOR_BY_DATE));
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public void printSortingMenu(PrintWriter printWriter) {
        MenuForSortingBooks.printMenu(printWriter);
    }

    @Override
    public List<String> getTitlesList() {
        return new ArrayList<>(titleListForBooks);
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
    public Book getByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        Book userBook;
        int pages;
        String name;
        boolean isBorrowed;
        String author;
        Date dateOfIssue;

        name = inputHandlerForLiterature.getUserLiteratureName();
        pages = inputHandlerForLiterature.getUserLiteraturePages();
        isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        author = inputHandlerForLiterature.getUserLiteratureAuthor();
        dateOfIssue = inputHandlerForLiterature.getUserLiteratureDateOfIssue();


        userBook = new Book(name, pages, isBorrowed, author, dateOfIssue);
        return userBook;
    }

    @Override
    public Book getRandomItem(Random random) {
        random = new Random();
        Book randomBook = new Book(
                getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false,
                getRandomString(random.nextInt(10), random),
                new Date(random.nextInt(1000000)));
        return randomBook;
    }
}
