package org.vshmaliukh;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.constants.enums_for_menu.*;
import org.vshmaliukh.handlers.*;
import org.vshmaliukh.handlers.ItemHandlers.BookHandler;
import org.vshmaliukh.handlers.ItemHandlers.GazetteHandler;
import org.vshmaliukh.handlers.ItemHandlers.MagazineHandler;
import org.vshmaliukh.services.gson_service.ItemGsonHandler;
import org.vshmaliukh.services.gson_service.ItemGsonHandlerOneFile;
import org.vshmaliukh.services.gson_service.ItemGsonHandlerPerType;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.input_services.InputHandlerForUser;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForLiterature;
import org.vshmaliukh.services.print_table_service.TablePrinter;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import static org.vshmaliukh.ConstantsForTerminal.*;
import static org.vshmaliukh.constants.enums_for_menu.MainMenu.getByIndex;

/**
 * @author ShmaliukhVlad
 * @version 1.1.3
 * Class which gives user interactive interface
 */
public class Terminal {

    private boolean isActiveTerminal;
    private int typeOfWorkWithFiles;

    private final Shelf shelf;
    private User user;

    private final Scanner scanner;
    private final PrintWriter printWriter;

    private final InputHandlerForUser inputHandlerForUser;
    private InputHandlerForLiterature inputHandlerForLiterature;

    private ConvertorToStringForLiterature convertorToStringForLiterature;

    private ItemGsonHandler itemGsonHandler;
    private Random random;

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
        String property = System.getProperty("user.home");
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                itemGsonHandler = new ItemGsonHandlerOneFile(property, user.getName());
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                itemGsonHandler = new ItemGsonHandlerPerType(property, user.getName());
                break;
            default:
                itemGsonHandler = new ItemGsonHandlerOneFile(property, user.getName());
                break;
        }
    }

    public void startWork(boolean userMode) throws ParseException {
        printWriter.println("Terminal START");

        startWithUserConfig(userMode);
        setUpTypeOfWorkWithFiles();
        initServicesForTerminal();
        informAboutFileTypeWork(typeOfWorkWithFiles);

        itemGsonHandler.readListFromFile().forEach(shelf::addLiteratureObject);
        while (isActiveTerminal()) {
            generateUserInterface();
            itemGsonHandler.saveToFile(shelf.getAllLiteratureObjects());
        }
    }

    private void initServicesForTerminal() {
        random = new Random();
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
            case FILE_MODE_WORK_WITH_ONE_FILE:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                printWriter.println("FILE_MODE_WORK_WITH_FILE_PER_TYPE");
                break;
            default:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
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

            // TODO use new method for printing menu for items actions
            case PRINT_SORTED_BOOKS:
                clarificationForSortingBooks();
                break;
            case PRINT_SORTED_MAGAZINES:
                clarificationForSortingMagazines(); // TODO extract method to magazine handler
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
        TablePrinter.printTable(printWriter, Collections.emptyList(), // FIXME create validation for choosing column titles list
                convertorToStringForLiterature.getTable(shelf.getAllLiteratureObjects()), false);
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
        List<Magazine> magazines = Utils.getItemsByType(Magazine.class, shelf.getAllLiteratureObjects());
        MagazineHandler magazineHandler = ItemHandlerProvider.getMagazineHandler();
        List<Magazine> magazineList = magazineHandler.clarificationForSortingItems(magazines, getUserChoice(), printWriter);
        TablePrinter.printTable(printWriter, magazineHandler.getTitlesList(), convertorToStringForLiterature.getTable(magazineList), true);
    }

    /**
     * Method gives ability to choose method for sorting Books and print sorted list
     */
    private void clarificationForSortingBooks() {
        List<Book> itemsByType = Utils.getItemsByType(Book.class, shelf.getAllLiteratureObjects());
        BookHandler bookHandler = ItemHandlerProvider.getBookHandler();
        bookHandler.clarificationForSortingItems(itemsByType, getUserChoice(), printWriter);
        TablePrinter.printTable(printWriter, bookHandler.getTitlesList(), convertorToStringForLiterature.getTable(itemsByType), true);


    }

    /**
     * Method gives ability to choose method for sorting Gazettes and print sorted list
     */
    private void clarificationForSortingGazettes() {
        List<Gazette> gazetteList = Utils.getItemsByType(Gazette.class, shelf.getAllLiteratureObjects());
        GazetteHandler gazetteHandler = ItemHandlerProvider.getGazetteHandler();
        List<Gazette> sortedGazettes = gazetteHandler.clarificationForSortingItems(gazetteList, getUserChoice(), printWriter);
        TablePrinter.printTable(printWriter, gazetteHandler.getTitlesList(), convertorToStringForLiterature.getTable(sortedGazettes), true);
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object back to Shelf
     */
    private void menuForArrivingLiterature() {
        if (shelf.getLiteratureOutShelf().isEmpty()) {
            printWriter.println("No literature OUT shelf to arrive");
        } else {
            printWriter.println("Enter INDEX of Literature object to arrive one:");
            TablePrinter.printTable(printWriter, Collections.emptyList(), // FIXME create validation for choosing column titles list
                    convertorToStringForLiterature.getTable(shelf.getLiteratureOutShelf()), true);
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
            TablePrinter.printTable(printWriter, Collections.emptyList(), // FIXME create validation for choosing column titles list
                    convertorToStringForLiterature.getTable(shelf.getLiteratureInShelf()), true);
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
            TablePrinter.printTable(printWriter, Collections.emptyList(), // FIXME create validation for choosing column titles list
                    convertorToStringForLiterature.getTable(shelf.getLiteratureInShelf()), true);
            printWriter.println("Enter another value to return");
            shelf.deleteLiteratureObjectByIndex(getUserChoice());
        }
    }

    /**
     * Method give user ability to add new Literature object to Shelf
     */
    private void addNewLiteratureObject() {
        MenuForAddingLiterature byIndex = MenuForAddingLiterature.getByIndex(getUserChoice());
        Item item;
        switch (byIndex) {
            case ADD_CUSTOM_MAGAZINE:
                item = getUserMagazine();
                break;
            case ADD_CUSTOM_GAZETTE:
                item = getUserGazette();
                break;
            case ADD_CUSTOM_BOOK:
                item = getUserBook();
                break;
            case ADD_RANDOM_MAGAZINE:
                item = getRandomMagazine();
                break;
            case ADD_RANDOM_GAZETTE:
                item = getRandomGazette();
                break;
            case ADD_RANDOM_BOOK:
                item = getRandomBook();
                break;
            default:
                item = null;
                break;
        }
        shelf.addLiteratureObject(item);
        informAboutAddedLiteratureObject(item);
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
        return ItemHandlerProvider.getMagazineHandler().getByUserInput(inputHandlerForLiterature, printWriter);
    }

    /**
     * Method give ability to create custom Gazette
     *
     * @return user created Gazette
     */
    public Gazette getUserGazette() {
        return ItemHandlerProvider.getGazetteHandler().getByUserInput(inputHandlerForLiterature, printWriter);


    }

    /**
     * Method give ability to create custom Book
     *
     * @return user created Book
     */
    private Book getUserBook() {
        return ItemHandlerProvider.getBookHandler().getByUserInput(inputHandlerForLiterature, printWriter);
    }

    /**
     * Method forms new Magazine with random parameters (isBorrowed = false -> constant)
     *
     * @return Magazine with
     * random name (max string length = 20),
     * random number of pages (max = 1000)
     */
    public Magazine getRandomMagazine() {
        return ItemHandlerProvider.getMagazineHandler().getRandomItem(random);
        //his method is only for developer
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
        return ItemHandlerProvider.getBookHandler().getRandomItem(random);
        //his method is only for developer
    }

    /**
     * Method forms new Gazette with random parameters (isBorrowed = false -> constant)
     *
     * @return Gazette with
     * random name (max string length = 20),
     * random number of pages (max = 1000)
     */
    public Gazette getRandomGazette() {
        return ItemHandlerProvider.getGazetteHandler().getRandomItem(random);
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
}

