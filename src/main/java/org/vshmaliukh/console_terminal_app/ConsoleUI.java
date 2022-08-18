package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForUser;
import org.vshmaliukh.shelf.AbstractUI;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.services.menus.MainMenu;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForItems;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemUtils;
import org.vshmaliukh.shelf.shelf_handler.SqlLiteShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static org.vshmaliukh.console_terminal_app.SaveReadShelfHandler.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.TITLE_LIST;

public class ConsoleUI extends AbstractUI {

    public static final int WRONG_INPUT = -1;

    public static final String ENTER_ANOTHER_VALUE_TO_RETURN = "Enter another value to return";
    public static final String CHOOSE_TYPE_OF_SORTING = "Choose type of sorting:";
    public static final String NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING = "No available literature item IN shelf for sorting";

    boolean isActiveTerminal = true;

    final Scanner scanner;
    final PrintWriter printWriter;

    final ConsoleInputHandlerForUser consoleInputHandlerForUser;
    ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature;

    public ConsoleUI(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
        consoleInputHandlerForUser = new ConsoleInputHandlerForUser(scanner, printWriter);
    }

    public void configShelfHandler() { // todo refactor
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                shelfHandler = new ConsoleGsonShelfHandler(scanner, printWriter, user.getName(), typeOfWorkWithFiles);
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new ConsoleGsonShelfHandler(scanner, printWriter, user.getName(), typeOfWorkWithFiles);
                break;
            case FILE_MODE_WORK_WITH_SQLLITE:
                shelfHandler = new SqlLiteShelfHandler(user.getName());
                break;
            default:
                shelfHandler = new SqlLiteShelfHandler(user.getName());
                break;
        }
    }

    public void startWithUserConfig(boolean userMode) {
        setUpUserName(userMode);
    }

    public void setUpTypeOfWorkWithFiles() {
        typeOfWorkWithFiles = consoleInputHandlerForUser.getTypeOfWorkWithFiles();
        configShelfHandler();
        shelfHandler.setUpDataSaver(user.getName(), typeOfWorkWithFiles);
    }

    public void startWork(boolean userMode) {
        printWriter.println("Terminal START");

        startWithUserConfig(userMode);
        setUpTypeOfWorkWithFiles();
        initServicesForTerminal();
        informAboutFileTypeWork(typeOfWorkWithFiles);

        shelfHandler.readShelfItems();
        while (isActiveTerminal()) {
            generateUserInterface();
            shelfHandler.saveShelfItems();
        }
    }

    public void initServicesForTerminal() {
        consoleInputHandlerForLiterature = new ConsoleInputHandlerForLiterature(scanner, printWriter);
    }

    private void setUpUserName(boolean userMode) {
        if (userMode) {
            userLogin();
        } else {
            user = new User("no_user");
        }
    }

    private void userLogin() {
        user = new User(consoleInputHandlerForUser.getUserName());
    }

    public void informAboutFileTypeWork(int typeOfWorkWithFiles) {
        printWriter.println("Type of work with save/read shelf with files: ");
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                printWriter.println("FILE_MODE_WORK_WITH_FILE_PER_TYPE");
                break;
            case FILE_MODE_WORK_WITH_SQLLITE:
                printWriter.println("FILE_MODE_WORK_WITH_SQLLITE");
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
        MainMenu byIndex = MainMenu.getByIndex(getUserChoice());
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
        for (MenuItemClassType<?> sortingMenuItem : menuForSorting.getMenuItems()) {
            if (userChoice == sortingMenuItem.getIndex()) {
                menuForForSortingItemsByType(sortingMenuItem);
            }
        }
    }

    private <T extends Item> void menuForForSortingItemsByType(MenuItemClassType<T> sortingMenuItem) {
        Class<T> classType = sortingMenuItem.getClassType();
        ItemHandler<T> handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
        List<T> typedItemList = ItemUtils.getItemsByType(classType, shelfHandler.getShelf().getAllLiteratureObjects());
        handlerByClass.printSortingMenu(printWriter);
        List<T> sortedList = handlerByClass.clarificationForSortingItems(typedItemList, getUserChoice(), printWriter);
        new PlainTextTableHandler(printWriter, TITLE_LIST, ConvertorToStringForItems.getTable(sortedList), true).print();
    }

    /**
     * Method print info Shelf and it's Literature objects
     */
    public void printCurrentStateOfShelf() {
        printWriter.println("Current state of Shelf:");
        new PlainTextTableHandler(printWriter, TITLE_LIST, ConvertorToStringForItems.getTable(shelfHandler.getShelf().getAllLiteratureObjects()), false).print();
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
        List<Item> itemList = shelfHandler.readLiteratureOutShelf();
        if (itemList.isEmpty()) {
            printWriter.println("No literature OUT shelf to arrive");
        } else {
            printWriter.println("Enter INDEX of Literature object to arrive one:");
            new PlainTextTableHandler(printWriter, TITLE_LIST, ConvertorToStringForItems.getTable(itemList), true).print();
            printWriter.println(ENTER_ANOTHER_VALUE_TO_RETURN);
            shelfHandler.changeBorrowedStateOfItem(itemList, getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object from Shelf
     */
    private void menuForBorrowingLiterature() {
        List<Item> itemList = shelfHandler.readLiteratureInShelf();
        if (itemList.isEmpty()) {
            printWriter.println("No available literature IN shelf to borrow");
        } else {
            printWriter.println("Enter INDEX of Literature object to borrow one:");
            new PlainTextTableHandler(printWriter, TITLE_LIST, ConvertorToStringForItems.getTable(itemList), true).print();
            printWriter.println(ENTER_ANOTHER_VALUE_TO_RETURN);
            shelfHandler.changeBorrowedStateOfItem(itemList, getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to delete some Literature object in Shelf
     */
    private void menuForDeletingLiterature() {
        if (shelfHandler.readLiteratureInShelf().isEmpty()) {
            printWriter.println("No available literature IN shelf to delete");
        } else {
            printWriter.println("Enter INDEX of Literature object to delete one:");
            new PlainTextTableHandler(printWriter, TITLE_LIST, ConvertorToStringForItems.getTable(shelfHandler.readLiteratureInShelf()), true).print();
            printWriter.println(ENTER_ANOTHER_VALUE_TO_RETURN);
            shelfHandler.deleteLiteratureObjectByIndex(getUserChoice());
        }
    }

    /**
     * Method give user ability to add new Literature object to Shelf
     */
    private void addNewLiteratureObject() {
        int userChoice = getUserChoice();
        GeneratedMenuForAdding generatedMenu = new GeneratedMenuForAdding();
        if (userChoice > 0 && userChoice - 1 < generatedMenu.getMenuItems().size()) {
            MenuItemClassType<?> menuItem = generatedMenu.getMenuItems().get(userChoice - 1);
            Class<? extends Item> classType = menuItem.getClassType();
            ItemHandler<? extends Item> handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            Item item;
            if ((userChoice - 1) % 2 == 0) {
                item = handlerByClass.getItemByUserInput(consoleInputHandlerForLiterature, printWriter);
            } else {
                item = handlerByClass.getRandomItem(random);
            }
            shelfHandler.addLiteratureObject(item);
            printWriter.println(item + " has added to shelf");
        } else {
            printWriter.println("Wrong input");
        }
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
