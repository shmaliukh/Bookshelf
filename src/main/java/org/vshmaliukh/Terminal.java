package org.vshmaliukh;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.constants.enums_for_menu.*;
import org.vshmaliukh.services.LiteratureSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.gson_service.GsonHandler;
import org.vshmaliukh.services.input_services.InputHandlerForUser;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForLiterature;
import org.vshmaliukh.services.print_table_service.TablePrinter;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import static org.vshmaliukh.constants.ConstantsLiteratureSorterHandler.*;
import static org.vshmaliukh.constants.ConstantsForTerminal.*;
import static org.vshmaliukh.constants.enums_for_menu.MainMenu.getByIndex;

/**
 * @author ShmaliukhVlad
 * @version 1.1.3
 * Class which gives user interactive interface
 */
public class Terminal {

    private boolean isActiveTerminal;
    private int typeOfWorkWithFiles;

    private Shelf shelf;
    private User user;

    private final Scanner scanner;
    private final PrintWriter printWriter;
    private final InputHandlerForUser inputHandlerForUser;
    private InputHandlerForLiterature inputHandlerForLiterature;

    private ConvertorToStringForLiterature convertorToStringForLiterature;

    private GsonHandler gsonHandler;
    private Random randomNumber;

    // TODO delete title list if new version is ready
    List<String> titleListForBooks = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED", "AUTHOR", "DATE"));
    List<String> titleListForMagazine = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED"));
    List<String> titleListForGazette = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED")); // = titleListForMagazine;

    public Terminal(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;

        isActiveTerminal = true;
        shelf = new Shelf(printWriter);

        inputHandlerForUser = new InputHandlerForUser(scanner, printWriter);
    }

    public void startWithUserConfig(boolean userMode) {
        setUpUserName(userMode);
    }

    private void setUpTypeOfWorkWithFiles() {
        typeOfWorkWithFiles = inputHandlerForUser.getTypeOfWorkWithFiles();
    }

    public void startWork(boolean userMode) throws ParseException {
        printWriter.println("Terminal START");

        startWithUserConfig(userMode);
        setUpTypeOfWorkWithFiles();
        initServicesForTerminal(typeOfWorkWithFiles);
        informAboutFileTypeWork(typeOfWorkWithFiles);

        shelf = gsonHandler.readShelfFromGson();
        while (isActiveTerminal()) {
            generateUserInterface();
            gsonHandler.saveShelfInGson(shelf);
        }
    }

    private void initServicesForTerminal(int typeOfWorkWithFiles) {
        randomNumber = new Random();
        gsonHandler = new GsonHandler(typeOfWorkWithFiles, user.getName(), printWriter);
        inputHandlerForLiterature = new InputHandlerForLiterature(scanner, printWriter);

        convertorToStringForLiterature = new ConvertorToStringForLiterature();
    }

    private void setUpUserName(boolean userMode) {
        if (userMode) {
            userLogin();
        } else {
            user = new User("no_user");
        }
    }

    private void userLogin() {
        user = new User(inputHandlerForUser.getUserName());
    }

    private void informAboutFileTypeWork(int typeOfWorkWithFiles) {
        printWriter.print("Type of work with save/read shelf with files: ");
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_NO_WORK_WITH_FILES:
                printWriter.println("FILE_MODE_NO_WORK_WITH_FILES");
                break;
            case FILE_MODE_WORK_WITH_ONE_FILE:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                printWriter.println("FILE_MODE_WORK_WITH_FILE_PER_TYPE");
                break;
            default:
                break;
        }
    }

    /**
     * Method which gives opportunity to choose command by entering number of item to execute
     * Continually print menu items and wait for user choice
     * Works till user enter '0' (Exit)
     */
    private void generateUserInterface() throws ParseException {
        printMainMenu();
        MainMenu byIndex = getByIndex(getUserChoice());
        switch (byIndex) {
            case ADD_NEW_LITERATURE:
                printMenuForAddingLiterature();
                addNewLiteratureObject();
                break;
            case DELETE_LITERATURE:
                menuForDeletingLiterature();
                break;
            case BORROW_LITERATURE:
                menuForBorrowingLiterature();
                break;
            case ARRIVE_LITERATURE:
                menuForArrivingLiterature();
                break;
            case PRINT_SORTED_BOOKS:
                clarificationForSortingBooks();
                break;
            case PRINT_SORTED_MAGAZINES:
                clarificationForSortingMagazines();
                break;
            case PRINT_SORTED_GAZETTES:
                clarificationForSortingGazettes();
                break;
            case PRINT_SHELF:
                printCurrentStateOfShelf();
                break;
            case EXIT:
                closeTerminal();
                break;
            default:
                printWriter.println("Wrong input");
                break;
        }
    }

    /**
     * Method print info Shelf and it's Literature objects
     */
    private void printCurrentStateOfShelf() {
        printWriter.println("Current state of Shelf:");
        TablePrinter.printTable(printWriter, titleListForBooks, convertorToStringForLiterature.getTable(shelf.getAllLiteratureObjects()), false);
    }

    /**
     * Method close current terminal
     */
    private void closeTerminal() {
        this.stop();
        printWriter.println("Terminal STOP");
    }

    /**
     * Method gives ability to choose method for sorting Magazines and print sorted list
     */
    private void clarificationForSortingMagazines() {
        if (shelf.getMagazines().isEmpty()) {
            printWriter.println("No available magazines IN shelf for sorting");
        } else {
            printMenuForMagazinesSorting();
            printSortedMagazines(getUserChoice());
        }

    }

    /**
     * Method gives ability to choose method for sorting Books and print sorted list
     */
    private void clarificationForSortingBooks() {
        if (shelf.getBooks().isEmpty()) {
            printWriter.println("No available books IN shelf for sorting");
        } else {
            printMenuForBooksSorting();
            printSortedBooks(getUserChoice());
        }
    }

    /**
     * Method gives ability to choose method for sorting Gazettes and print sorted list
     */
    private void clarificationForSortingGazettes() {
        if (shelf.getGazettes().isEmpty()) {
            printWriter.println("No available gazettes IN shelf for sorting");
        } else {
            printMenuForGazettesSorting();
            printSortedGazettes(getUserChoice());
        }

    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object back to Shelf
     */
    private void menuForArrivingLiterature() {
        if (shelf.getLiteratureOutShelf().isEmpty()) {
            printWriter.println("No literature OUT shelf to arrive");
        } else {
            printWriter.println("Enter INDEX of Literature object to arrive one:");
            TablePrinter.printTable(printWriter, titleListForBooks, convertorToStringForLiterature.getTable(shelf.getLiteratureOutShelf()), true);
            printWriter.println("Enter another value to return");
            shelf.arriveLiteratureObjectFromShelfByIndex(getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object from Shelf
     */
    private void menuForBorrowingLiterature() {
        if (shelf.getLiteratureInShelf().isEmpty()) {
            printWriter.println("No available literature IN shelf to borrow");
        } else {
            printWriter.println("Enter INDEX of Literature object to borrow one:");
            TablePrinter.printTable(printWriter, titleListForBooks, convertorToStringForLiterature.getTable(shelf.getLiteratureInShelf()), true);
            printWriter.println("Enter another value to return");
            shelf.borrowLiteratureObjectFromShelfByIndex(getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to delete some Literature object in Shelf
     */
    private void menuForDeletingLiterature() {
        if (shelf.getLiteratureInShelf().isEmpty()) {
            printWriter.println("No available literature IN shelf to delete");
        } else {
            printWriter.println("Enter INDEX of Literature object to delete one:");
            TablePrinter.printTable(printWriter, titleListForBooks, convertorToStringForLiterature.getTable(shelf.getLiteratureInShelf()), true);
            printWriter.println("Enter another value to return");
            shelf.deleteLiteratureObjectByIndex(getUserChoice());
        }
    }

    /**
     * Method give user ability to add new Literature object to Shelf
     */
    private void addNewLiteratureObject() throws ParseException {
        MenuForAddingLiterature byIndex = MenuForAddingLiterature.getByIndex(getUserChoice());
        switch (byIndex) {
            case ADD_CUSTOM_MAGAZINE:
                shelf.addLiteratureObject(getUserMagazine());
                break;
            case ADD_CUSTOM_BOOK:
                shelf.addLiteratureObject(getUserBook());
                break;
            case ADD_RANDOM_MAGAZINE:
                shelf.addLiteratureObject(getRandomMagazine());
                break;
            case ADD_RANDOM_BOOK:
                shelf.addLiteratureObject(getRandomBook());
                break;
            default:
                break;
        }
    }

    /**
     * Method simply inform user about added Literature object
     */
    private void informAboutAddedLiteratureObject(Item item) {
        printWriter.println(item + " has added to shelf");
    }

    /**
     * Method give ability to create custom Magazine
     *
     * @return user created Magazine
     */
    public Magazine getUserMagazine() {
        Magazine userMagazine;
        String name;
        int pages;
        boolean isBorrowed;

        name = inputHandlerForLiterature.getUserLiteratureName();
        pages = inputHandlerForLiterature.getUserLiteraturePages();
        isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();

        userMagazine = new Magazine(name, pages, isBorrowed);
        informAboutAddedLiteratureObject(userMagazine);
        return userMagazine;
    }

    /**
     * Method give ability to create custom Book
     *
     * @return user created Book
     */
    private Book getUserBook() throws ParseException {
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
        informAboutAddedLiteratureObject(userBook);
        return userBook;
    }

    /**
     * Method forms new Magazine with random parameters (isBorrowed = false -> constant)
     *
     * @return Magazine with
     * random name (max string length = 20),
     * random number of pages (max = 1000)
     */
    public Magazine getRandomMagazine() {
        //his method is only for developer
        Magazine randomMagazine = new Magazine(
                getRandomString(randomNumber.nextInt(20)),
                randomNumber.nextInt(1000),
                false);

        informAboutAddedLiteratureObject(randomMagazine);
        return randomMagazine;
    }

    /**
     * Method forms new Book with random parameters (isBorrowed = false -> constant)
     *
     * @return Book with
     * random name (max string length = 20),
     * random number of pages (max = 1000),
     * random author (max string length = 10),
     * random date of issue (random number (up to 1 000 000)  milliseconds since January 1, 1970, 00:00:00)
     */
    public Book getRandomBook() {
        //his method is only for developer
        randomNumber = new Random();
        Book randomBook = new Book(
                getRandomString(randomNumber.nextInt(20)),
                randomNumber.nextInt(1000),
                false,
                getRandomString(randomNumber.nextInt(10)),
                new Date(randomNumber.nextInt(1000000)));

        informAboutAddedLiteratureObject(randomBook);
        return randomBook;
    }

    /**
     * Method which gives opportunity to get string with random characters ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ")
     *
     * @param length is value of expected string size
     * @return string with random symbols
     */
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = randomNumber.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * Method which gives opportunity to get user choice by entered integer value in console
     *
     * @return entered integer value from console
     */
    public int getUserChoice() {
        if (scanner.hasNextLine()) {
            String str = scanner.nextLine().replaceAll("[\\D]", "").trim();
            if (str.length() > 8) {
                str = str.substring(0, 8);
            }
            if (!str.equals("")) {
                return Integer.parseInt(str);
            }
        }
        return WRONG_INPUT;
    }

    private void printMainMenu() {
        MainMenu.printMainMenu(printWriter);
    }

    private void printMenuForBooksSorting() {
        MenuForSortingBooks.printMenu(printWriter);
    }

    private void printMenuForMagazinesSorting() {
        MenuForSortingMagazines.printMenu(printWriter);
    }

    private void printMenuForGazettesSorting() {
        MenuForSortingGazettes.printMenu(printWriter);
    }

    private void printMenuForAddingLiterature() {
        MenuForAddingLiterature.printMenu(printWriter);
    }

    public void stop() {
        scanner.close();
        printWriter.close();
        setActiveTerminal(false);
    }

    public boolean isActiveTerminal() {
        return isActiveTerminal;
    }

    public void setActiveTerminal(boolean activeTerminal) {
        this.isActiveTerminal = activeTerminal;
    }

    public void printSortedBooks(int typeOfSorting) {
        List<Item> bookList = new ArrayList<>();
        MenuForSortingBooks byIndex = MenuForSortingBooks.getByIndex(typeOfSorting);
        switch (byIndex) {
            case SORT_BOOKS_BY_NAME:
                bookList.addAll(
                        new LiteratureSorterHandler<>(shelf.getBooks())
                                .getSortedLiterature(BOOK_COMPARATOR_BY_NAME));
                break;
            case SORT_BOOKS_BY_PAGES_NUMBER:
                bookList.addAll(
                        new LiteratureSorterHandler<>(shelf.getBooks())
                                .getSortedLiterature(BOOK_COMPARATOR_BY_PAGES));
                break;
            case SORT_BOOKS_BY_AUTHOR:
                bookList.addAll(
                        new LiteratureSorterHandler<>(shelf.getBooks())
                                .getSortedLiterature(BOOK_COMPARATOR_BY_AUTHOR));
                break;
            case SORT_BOOKS_BY_DATE_OF_ISSUE:
                bookList.addAll(
                        new LiteratureSorterHandler<>(shelf.getBooks())
                                .getSortedLiterature(BOOK_COMPARATOR_BY_DATE));
                break;
            default:
                break;
        }
        TablePrinter.printTable(printWriter, titleListForBooks, convertorToStringForLiterature.getTable(bookList), true);
    }

    public void printSortedMagazines(int typeOfSorting) {
        List<Item> magazineList = new ArrayList<>();
        MenuForSortingMagazines byIndex = MenuForSortingMagazines.getByIndex(typeOfSorting);
        switch (byIndex) {
            case SORT_MAGAZINES_BY_NAME:
                magazineList.addAll( new LiteratureSorterHandler<>(shelf.getMagazines())
                        .getSortedLiterature(MAGAZINE_COMPARATOR_BY_NAME));
                break;
            case SORT_MAGAZINES_BY_PAGES:
                magazineList.addAll( new LiteratureSorterHandler<>(shelf.getMagazines())
                        .getSortedLiterature(MAGAZINE_COMPARATOR_BY_PAGES));
                break;
            default:
                break;
        }
        TablePrinter.printTable(printWriter, titleListForMagazine, convertorToStringForLiterature.getTable(magazineList), true);
    }

    public void printSortedGazettes(int typeOfSorting) {
        List<Item> gazetteList = new ArrayList<>();
        MenuForSortingGazettes byIndex = MenuForSortingGazettes.getByIndex(typeOfSorting);
        switch (byIndex) {
            case SORT_GAZETTES_BY_NAME:
                gazetteList.addAll( new LiteratureSorterHandler<>(shelf.getGazettes())
                        .getSortedLiterature(GAZETTE_COMPARATOR_BY_NAME));
                break;
            case SORT_GAZETTES_BY_PAGES:
                gazetteList.addAll( new LiteratureSorterHandler<>(shelf.getGazettes())
                        .getSortedLiterature(GAZETTE_COMPARATOR_BY_PAGES));
                break;
            default:
                break;
        }
        TablePrinter.printTable(printWriter, titleListForGazette, convertorToStringForLiterature.getTable(gazetteList), true);
    }
}

