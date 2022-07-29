package org.vshmaliukh.terminal;

import org.vshmaliukh.ScannerWrapper;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.Shelf;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.GeneratedMenuForAdding;
import org.vshmaliukh.terminal.menus.GeneratedMenuForSorting;
import org.vshmaliukh.terminal.menus.MainMenu;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.terminal.services.gson_service.ItemGsonHandler;
import org.vshmaliukh.terminal.services.gson_service.ItemGsonHandlerOneFile;
import org.vshmaliukh.terminal.services.gson_service.ItemGsonHandlerPerType;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForUser;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;
import org.vshmaliukh.terminal.services.print_table_service.TablePrinter;

import java.io.*;
import java.util.*;

import static org.vshmaliukh.terminal.menus.MainMenu.getByIndex;

/**
 * @author ShmaliukhVlad
 * @version 1.1.3
 * Class which gives user interactive interface
 */
public class Terminal {

    public static final String DATE_FORMAT_STR = "dd-MM-yyyy";

    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 1;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 2;

    public static final int WRONG_INPUT = -1;

    private boolean isActiveTerminal;
    private int typeOfWorkWithFiles;

    public final Shelf shelf;
    private User user;

    private final ScannerWrapper scanner;
    private final PrintWriter printWriter;

    private final InputHandlerForUser inputHandlerForUser;
    private InputHandlerForLiterature inputHandlerForLiterature;

    private ConvertorToStringForLiterature convertorToStringForLiterature;

    public ItemGsonHandler itemGsonHandler;
    private Random random;

    public Terminal(ScannerWrapper scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;

        isActiveTerminal = true;
        shelf = new Shelf(printWriter);

        inputHandlerForUser = new InputHandlerForUser(scanner, printWriter);
    }

    public void startWithUserConfig(boolean userMode) {
        setUpUserName(userMode);
    }

    public void setUpTypeOfWorkWithFiles() {
        typeOfWorkWithFiles = inputHandlerForUser.getTypeOfWorkWithFiles();
        String property = System.getProperty("user.home");
        setUpGsonHandler(property);
    }

    private void setUpGsonHandler(String property) {
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

    public boolean startWork(boolean userMode) {
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
        return false;
    }

    public void initServicesForTerminal() {
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

    public void informAboutFileTypeWork(int typeOfWorkWithFiles) {
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
    public void generateUserInterface() {
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
            case SORT_LITERATURE:
                menuForSortingLiterature();
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

    private void menuForSortingLiterature() {
        GeneratedMenu menuForSorting = new GeneratedMenuForSorting();
        menuForSorting.printMenu(printWriter);
        int userChoice = getUserChoice();
        for (MenuItemClassType sortingMenuItem : menuForSorting.getMenuItems()) {
            if (userChoice == sortingMenuItem.getIndex()) {
                menuForForSortingItemsByType(sortingMenuItem);
            }
        }
    }

    private void menuForForSortingItemsByType(MenuItemClassType sortingMenuItem) {
        Class classType = sortingMenuItem.getClassType();
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
        List typedItemList = Utils.getItemsByType(classType, shelf.getAllLiteratureObjects());
        handlerByClass.printSortingMenu(printWriter);
        List<Item> sortedList = handlerByClass.clarificationForSortingItems(typedItemList, getUserChoice(), printWriter);
        TablePrinter.printTable(printWriter, convertorToStringForLiterature.getTable(sortedList), true);
    }

    /**
     * Method print info Shelf and it's Literature objects
     */
    private void printCurrentStateOfShelf() {
        printWriter.println("Current state of Shelf:");
        TablePrinter.printTable(printWriter, convertorToStringForLiterature.getTable(shelf.getAllLiteratureObjects()), false);
    }

    /**
     * Method close current terminal
     */
    private void closeTerminal() {
        this.stop();
        printWriter.println("Terminal STOP");
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object back to Shelf
     */
    private void menuForArrivingLiterature() {
        if (shelf.getLiteratureOutShelf().isEmpty()) {
            printWriter.println("No literature OUT shelf to arrive");
        } else {
            printWriter.println("Enter INDEX of Literature object to arrive one:");
            TablePrinter.printTable(printWriter, convertorToStringForLiterature.getTable(shelf.getLiteratureOutShelf()), true);
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
            TablePrinter.printTable(printWriter,
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
            TablePrinter.printTable(printWriter,
                    convertorToStringForLiterature.getTable(shelf.getLiteratureInShelf()), true);
            printWriter.println("Enter another value to return");
            shelf.deleteLiteratureObjectByIndex(getUserChoice());
        }
    }

    /**
     * Method give user ability to add new Literature object to Shelf
     */
    private void addNewLiteratureObject() {
        int userChoice = getUserChoice();
        GeneratedMenuForAdding generatedMenu = new GeneratedMenuForAdding();
        if (userChoice > 0 && userChoice - 1 < generatedMenu.getMenuItems().size()) {
            Class classType = generatedMenu.getMenuItems().get(userChoice - 1).getClassType();
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            Item item;
            if ((userChoice - 1) % 2 == 0) {
                item = handlerByClass.getItemByUserInput(inputHandlerForLiterature, printWriter);
            } else {
                item = handlerByClass.getRandomItem(random);
            }
            shelf.addLiteratureObject(item);
            informAboutAddedLiteratureObject(item);
        } else {
            printWriter.println("Wrong input");
        }
    }

    /**
     * Method simply inform user about added Literature object
     */
    private void informAboutAddedLiteratureObject(Item item) {
        printWriter.println(item + " has added to shelf");
    }

    /**
     * Method which gives opportunity to get user choice by entered integer value in console
     *
     * @return entered integer value from console
     */
    public int getUserChoice() {
        if (scanner.hasNextLine() && !scanner.nextLine().equals("")) {
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
        GeneratedMenu generatedMenu = new GeneratedMenuForAdding();
        generatedMenu.printMenu(printWriter);
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

    public User getUser() {
        return user;
    }
}

