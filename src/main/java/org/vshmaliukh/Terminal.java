package org.vshmaliukh;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.services.gsonService.GsonHandler;
import org.vshmaliukh.services.UserInputHandler;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import static org.vshmaliukh.constants.ConstantValues.*;
import static org.vshmaliukh.constants.ConstantsForTerminal.*;

/**
 * @author ShmaliukhVlad
 * @version 1.1.3
 * Class which gives user interactive interface
 */
public class Terminal {

    private boolean isActiveTerminal;

    private Shelf shelf;
    private User user;

    private Scanner scanner;
    private PrintWriter printWriter;

    private UserInputHandler userInputHandler;
    private GsonHandler gsonHandler;
    private Random randomNumber;

    public Terminal(Scanner scanner, PrintWriter printWriter){
        this.scanner = scanner;
        this.printWriter = printWriter;
        isActiveTerminal = true;
        shelf = new Shelf(printWriter);

        randomNumber = new Random();
        userInputHandler = new UserInputHandler();
    }

    public void startWork(int typeOfWorkWithFiles, boolean userMode) throws ParseException, IOException{
        printWriter.println("Terminal START");
        informAboutFileSaveReadType(typeOfWorkWithFiles); // TODO rename method

        setUpUserName(userMode);

        gsonHandler = new GsonHandler(typeOfWorkWithFiles ,user.getName(), printWriter);

        shelf = gsonHandler.readShelfFromGson();
        while (isActiveTerminal()){
            generateUserInterface();
            gsonHandler.saveShelfInGson(shelf);
        }
    }

    private void setUpUserName(boolean userMode) {
        if(userMode){
            userLogin();
        }
        else {
            user = new User("no_user");
        }
    }

    private void userLogin() {
        user = new User(userInputHandler.getUserName(scanner,printWriter));
    }

    private void informAboutFileSaveReadType(int typeOfWorkWithFiles) {
        printWriter.print("Type of work with save/read shelf with files: ");
        switch (typeOfWorkWithFiles){
            case SAVE_READ_ONE_FILE:
                printWriter.println("SAVE_READ_ONE_FILE");
                break;
            case SAVE_READ_TWO_FILES:
                printWriter.println("SAVE_READ_TWO_FILES");
                break;
            default:
                printWriter.println("DEFAULT (no work with files)");
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
        switch (getUserChoice()) {
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
        String tab = "\t";
        printWriter.println("Current state of Shelf:");
        printWriter.println("literature IN {");
        shelf.getLiteratureInShelf()
                .forEach(o -> printWriter.print(tab + o.toString()));
        printWriter.println("}");
        printWriter.println("literature OUT {");
        shelf.getLiteratureOutShelf()
                .forEach(o -> printWriter.print(tab + o.toString()));
        printWriter.println("}");
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
        if(shelf.getMagazines().isEmpty()){
            printWriter.println("No available magazines IN shelf for sorting");
        }
        else {
            printMenuForMagazinesSorting();
            shelf.printSortedMagazines(getUserChoice());
        }

    }

    /**
     * Method gives ability to choose method for sorting Books and print sorted list
     */
    private void clarificationForSortingBooks() {
        if(shelf.getBooks().isEmpty()){
            printWriter.println("No available books IN shelf for sorting");
        }
        else {
            printMenuForBooksSorting();
            shelf.printSortedBooks(getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object back to Shelf
     */
    private void menuForArrivingLiterature() {
        if(shelf.getLiteratureOutShelf().isEmpty()){
            printWriter.println("No literature OUT shelf to arrive");
        }
        else {
            printWriter.println("Enter INDEX of Literature object to arrive one:");
            for (int i = 0; i < shelf.getLiteratureOutShelf().size(); i++) {
                printWriter.print( (i+1) + " " +  shelf.getLiteratureOutShelf().get(i).toString());
            }
            shelf.arriveLiteratureObjectFromShelfByIndex(getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object from Shelf
     */
    private void menuForBorrowingLiterature() {
        if(shelf.getLiteratureInShelf().isEmpty()){
            printWriter.println("No available literature IN shelf to borrow");
        }
        else {
            printWriter.println("Enter INDEX of Literature object to borrow one:");
            printLiteratureListWithIndex();
            shelf.borrowLiteratureObjectFromShelfByIndex(getUserChoice());
        }
    }

    /**
     * Method print menu with necessary information when user needs to delete some Literature object in Shelf
     */
    private void menuForDeletingLiterature() {
        if(shelf.getLiteratureInShelf().isEmpty()){
            printWriter.println("No available literature IN shelf to delete");
        }
        else {
            printWriter.println("Enter INDEX of Literature object to delete one:");
            printLiteratureListWithIndex();
            shelf.deleteLiteratureObjectByIndex(getUserChoice());
        }
    }

    private void printLiteratureListWithIndex() {
        for (int i = 0; i < shelf.getLiteratureInShelf().size(); i++) {
            printWriter.print( (i+1) + " " +  shelf.getLiteratureInShelf().get(i).toString());
        }
    }

    /**
     * Method give user ability to add new Literature object to Shelf
     */
    private void addNewLiteratureObject() throws ParseException {
        switch (getUserChoice()) {
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
    private void informAboutAddedLiteratureObject(Literature literature) {
        printWriter.println(literature + " has added to shelf");
    }

    /**
     * Method give ability to create custom Magazine
     * @return user created Magazine
     */
    public Magazine getUserMagazine() {
        Magazine userMagazine;
        String name;
        int pages;
        boolean isBorrowed;

        name = userInputHandler.getUserLiteratureName(scanner, printWriter);
        pages = userInputHandler.getUserLiteraturePages(scanner, printWriter);
        isBorrowed = userInputHandler.getUserLiteratureIsBorrowed(scanner, printWriter);

        userMagazine = new Magazine(name, pages, isBorrowed);
        informAboutAddedLiteratureObject(userMagazine);
        return userMagazine;
    }

    /**
     * Method give ability to create custom Book
     * @return user created Book
     */
    private Book getUserBook() throws ParseException {
        Book userBook;
        int pages;
        String name;
        boolean isBorrowed;
        String author;
        Date dateOfIssue;

        name = userInputHandler.getUserLiteratureName(scanner, printWriter);
        pages = userInputHandler.getUserLiteraturePages(scanner, printWriter);
        isBorrowed = userInputHandler.getUserLiteratureIsBorrowed(scanner, printWriter);
        author = userInputHandler.getUserLiteratureAuthor(scanner, printWriter);
        dateOfIssue = userInputHandler.getUserDateOfIssue(scanner, printWriter);

        userBook = new Book(name, pages, isBorrowed, author, dateOfIssue);
        informAboutAddedLiteratureObject(userBook);
        return userBook;
    }

    /**
     * Method forms new Magazine with random parameters (isBorrowed = false -> constant)
     * @return Magazine with
     * random name (max string length = 20),
     * random number of pages (max = 1000)
     */
    public Magazine getRandomMagazine() {
        Magazine randomMagazine = new Magazine(
                getRandomString(randomNumber.nextInt(20)),
                randomNumber.nextInt(1000),
                false);

        informAboutAddedLiteratureObject(randomMagazine);
        return randomMagazine;

    }

    /**
     * Method forms new Book with random parameters (isBorrowed = false -> constant)
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
     * @return entered integer value from console
     */
    public int getUserChoice(){
        if (scanner.hasNextLine()){
            String str = scanner.nextLine().replaceAll("[\\D]", "").trim();
            if(str.length() > 8){
                str = str.substring(0,8);
            }
            if (!str.equals("")) {
                return Integer.parseInt(str);
            }
        }
        return WRONG_INPUT;
    }

    /**
     * Method which simply print main menu
     */
    private void printMainMenu(){
        printWriter.println(
                        "\n" +
                        "Enter number of  command you wand to execute: (program ignores all not number symbols)" +"\n" +
                        "1 - Add new Literature object to Shelf" +"\n" +
                        "2 - Delete  Literature object by index from Shelf" +"\n" +
                        "3 - Borrow  Literature object by index from Shelf" +"\n" +
                        "4 - Arrive  Literature object by index back to Shelf" +"\n" +
                        "5 - Print list of available Books sorted by parameter..." +"\n" +
                        "6 - Print list of available Magazines sorted by parameter..." +"\n" +
                        "9 - Print current state of Shelf" +"\n" +
                        "0 - Exit");
    }

    /**
     * Method which simply print menu items for sorting books
     */
    private void printMenuForBooksSorting(){
        printWriter.println(
                        "Choose type of sorting:" +"\n" +
                        "1 - Sort by 'name' value" +"\n" +
                        "2 - Sort by 'author' value" +"\n" +
                        "3 - Sort by 'page number' value" +"\n" +
                        "4 - Sort by 'date' value" +"\n" +
                        "Enter another value to return");
    }

    /**
     * Method which simply print menu items for sorting magazines
     */
    private void printMenuForMagazinesSorting(){
        printWriter.println(
                        "Choose type of sorting:" +"\n" +
                        "1 - Sort by 'name' value" +"\n" +
                        "2 - Sort by 'page' value" +"\n" +
                        "Enter another value to return");
    }

    /**
     * Method which simply print menu items for adding literature obj
     */
    private void printMenuForAddingLiterature(){
        printWriter.println(
                         "Choose type of literature you want to add:" + "\n" +
                         "1 - Magazine" + "\n" +
                         "2 - Book" + "\n" +
                         "3 - Random Magazine" + "\n" +
                         "4 - Random Book" + "\n" +
                         "Enter another value to return");
    }

    public void stop(){
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

@Data
@AllArgsConstructor
class User {
    private String name;
}

