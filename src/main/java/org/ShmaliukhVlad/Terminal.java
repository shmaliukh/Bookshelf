package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.serices.UserInput;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.ShmaliukhVlad.constants.ConstantValues.*;

/**
 * @author ShmaliukhVlad
 * @version 1.1.3
 * Class which gives user interactive interface
 */
public class Terminal {
    private boolean play;

    private final Shelf shelf;
    private Scanner scanner;
    private PrintStream printStream;

    public Terminal(){
        this(System.in, System.out);
    }

    public Terminal(InputStream inputStream, PrintStream printStream){
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
        shelf = new Shelf();
        play = true;
    }

    /**
     * Method simulates Terminal work like a real one
     */
    public void startWork(){
        printStream.println("Terminal START");

        while (isPlay()){
            generateUserInterface();
        }
    }

    /**
     * Method which gives opportunity to choose command by entering number of item to execute
     * Continually print menu items and wait for user choice
     * Works till user enter '0' (Exit)
     */
    private void generateUserInterface() {
        printMainMenu();

        switch (getUserChoice()) {
            case ADD_NEW_LITERATURE -> {
                printMenuForAddingLiterature();
                addNewLiteratureObject();
            }
            case DELETE_LITERATURE -> {
                printMenuForDeletingLiterature();
                shelf.deleteLiteratureObjectByIndex(getUserChoice());
            }
            case BORROW_LITERATURE -> {
                printMenuForBorrowingLiterature();
                shelf.borrowLiteratureObjectFromShelfByIndex(getUserChoice());
            }
            case ARRIVE_LITERATURE -> {
                printMenuForArrivingLiterature();
                shelf.arriveLiteratureObjectFromShelfByIndex(getUserChoice());
            }
            case PRINT_SORTED_BOOKS -> clarificationForSortingBooks();
            case PRINT_SORTED_MAGAZINES -> clarificationForSortingMagazines();
            case SAVE_SHELF_IN_FILE -> saveShelf();
            case DESERIALIZE -> deserializeShelf();
            case PRINT_SHELF -> printCurrentStateOfShelf();
            case EXIT -> closeTerminal();
            case WRONG_INPUT -> printStream.println("Wrong input");
            default -> printStream.println("Wrong input");
        }
    }

    /**
     * Method print info Shelf and it's Literature objects
     */
    private void printCurrentStateOfShelf() {
        String tab = "\t";
        printStream.println("Current state of Shelf:");
        printStream.println("literature IN {");
        shelf.getLiteratureInShelf()
                .forEach(o -> printStream.print(tab + o.getPrintableLineOfLiteratureObject()));
        printStream.println("}");
        printStream.println("literature OUT {");
        shelf.getLiteratureOutShelf()
                .forEach(o -> printStream.print(tab + o.getPrintableLineOfLiteratureObject()));
        printStream.println("}");
    }

    /**
     * Method close current terminal
     */
    private void closeTerminal() {
        askUserIfNeedToSaveShelf();
        setPlay(false);
        printStream.println("Terminal STOP");
    }

    /**
     * Method gives a choice to save the current state of the shelf
     */
    private void askUserIfNeedToSaveShelf() {
        printStream.println("Do you want to record the current state of the shelf?");
        printStream.println("Enter '1' if you want to save");
        printStream.println("Press another key to exit without saving");
        if(getUserChoice() == SAVE_CURRENT_STATE_OF_SHELF){
            saveShelf();
        }
    }

    /**
     * Method deserializes all Literature objects from .out file save in current Shelf.
     */
    private void deserializeShelf() {
        printStream.println("If you need get saving from file '" + FILE_NAME + "'");
        printStream.println("you LOOSE ALL INFO about current SHELF");
        printStream.println("If you need to rewrite it enter '1'");
        printStream.println("Press another key to return");
        switch (getUserChoice()){
            case READ_FILE ->{
                try {
                    shelf.deserialize();
                    printStream.println("Deserialized");
                } catch (IOException e) {
                    //printStream.println("Serialization error");
                    System.err.println("Deserialization error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            default ->{
                printStream.println("Deserialization canceled");
            }
        }
        printStream.println();
        printCurrentStateOfShelf();
    }

    /**
     * Method serializes all Shelf's Literature objects in .out file.
     */
    private void saveShelf() {

        File file = new File(FILE_NAME);
        int userChoice = REWRITE_FILE;
        if(file.exists()){
            printStream.println("File " + FILE_NAME + " contains info about previous saving");
            printStream.println("If you need to rewrite it enter '1'");
            printStream.println("Press another key to return");
            userChoice =getUserChoice();
        }
        if(userChoice == REWRITE_FILE) {
            printStream.println("Shelf will be save as '" + FILE_NAME + "'");
            shelf.saveShelfToFile(FILE_NAME);
        }
    }

    /**
     * Method gives ability to choose method for sorting Magazines and print sorted list
     */
    private void clarificationForSortingMagazines() {
        printMenuForMagazinesSorting();
        switch (getUserChoice()) {
            case SORT_MAGAZINES_BY_NAME -> {
                shelf.printSortedMagazinesByName();
            }
            case SORT_MAGAZINES_BY_PAGES_NUMBER -> {
                shelf.printSortedMagazinesByPages();
            }
            default -> {
            }
        }
    }

    /**
     * Method gives ability to choose method for sorting Books and print sorted list
     */
    private void clarificationForSortingBooks() {
        printMenuForBooksSorting();
        switch (getUserChoice()) {
            case SORT_BOOKS_BY_NAME -> {
                shelf.printSortedBooksByName();
            }
            case SORT_BOOKS_BY_AUTHOR -> {
                shelf.printSortedBooksByAuthor();
            }
            case SORT_BOOKS_BY_PAGES_NUMBER -> {
                shelf.printSortedBooksByPages();
            }
            case SORT_BOOKS_BY_DATE_OF_ISSUE -> {
                shelf.printSortedBooksByDate();
            }
            default -> {
            }
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object back to Shelf
     */
    private void printMenuForArrivingLiterature() {
        printStream.println("Enter INDEX of Literature object to arrive one:");
        for (int i = 0; i < shelf.getLiteratureOutShelf().size(); i++) {
            printStream.print( (i+1) + " " +  shelf.getLiteratureOutShelf().get(i).getPrintableLineOfLiteratureObject());
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object from Shelf
     */
    private void printMenuForBorrowingLiterature() {
        printStream.println("Enter INDEX of Literature object to borrow one:");
        for (int i = 0; i < shelf.getLiteratureInShelf().size(); i++) {
            printStream.print( (i+1) + " " +  shelf.getLiteratureInShelf().get(i).getPrintableLineOfLiteratureObject());
        }
    }

    /**
     * Method print menu with necessary information when user needs to delete some Literature object in Shelf
     */
    private void printMenuForDeletingLiterature() {
        printStream.println("Enter INDEX of Literature object to delete one:");
        for (int i = 0; i < shelf.getLiteratureInShelf().size(); i++) {
            printStream.print( (i+1) + " " +  shelf.getLiteratureInShelf().get(i).getPrintableLineOfLiteratureObject());
        }
    }

    /**
     * Method give user ability to add new Literature object to Shelf
     */
    private void addNewLiteratureObject() {
        switch (getUserChoice()) {
            case ADD_CUSTOM_MAGAZINE -> shelf.addLiteratureObject(getUserMagazine());
            case ADD_CUSTOM_BOOK -> shelf.addLiteratureObject(getUserBook());
            case ADD_RANDOM_MAGAZINE -> shelf.addLiteratureObject(getRandomMagazine());
            case ADD_RANDOM_BOOK -> shelf.addLiteratureObject(getRandomBook());
            default -> {
            }
        }
    }

    /**
     * Method give ability to create custom Magazine
     * @return user created Magazine
     */
    public Magazine getUserMagazine() {
        Magazine userMagazineToAdd;
        String name;
        int pages;
        boolean isBorrowed = true;

        name = UserInput.getUserLiteratureName(scanner, printStream);
        pages = UserInput.getUserLiteraturePages(scanner, printStream);
        isBorrowed = UserInput.getUserLiteratureIsBorrowed(scanner, printStream);

        userMagazineToAdd = new Magazine(name, pages, isBorrowed);
        return userMagazineToAdd;
    }

    /**
     * Method give ability to create custom Book
     * @return user created Book
     */
    private Book getUserBook() {
        Book userBookToAdd;
        int pages;
        String name;
        boolean isBorrowed;
        String author;
        Date dateOfIssue;

        name = UserInput.getUserLiteratureName(scanner, printStream);
        pages = UserInput.getUserLiteraturePages(scanner, printStream);
        isBorrowed = UserInput.getUserLiteratureIsBorrowed(scanner, printStream);
        author = UserInput.getUserLiteratureAuthor(scanner, printStream);
        dateOfIssue = UserInput.getUserDateOfIssue(scanner, printStream);

        userBookToAdd = new Book(name, pages, isBorrowed, author, dateOfIssue);
        return userBookToAdd;
    }

    /**
     * Method forms new Magazine with random parameters (isBorrowed = false -> constant)
     * @return Magazine with
     * random name (max string length = 20),
     * random number of pages (max = 1000)
     */
    public Magazine getRandomMagazine() {
        Random randomNumber = new Random();
        return new Magazine(
                getRandomString(randomNumber.nextInt(20)),
                randomNumber.nextInt(1000),
                false);
    }

    /**
     * Method forms new Book with random parameters (isBorrowed = false -> constant)
     * @return Book with
     * random name (max string length = 20),
     * random number of pages (max = 1000),
     * random author (max string length = 10),
     * random date of issue (today minus random number of days (up to one year)),
     */
    public Book getRandomBook() {
        Random randomNumber = new Random();
        return new Book(
                getRandomString(randomNumber.nextInt(20)),
                randomNumber.nextInt(1000),
                false,
                getRandomString(randomNumber.nextInt(10)),
                new Date(System.currentTimeMillis()));
                        //.before(Period.ofDays((new Random().nextInt(365 * 70))))); TODO

    }

    /**
     * Method which gives opportunity to get string with random characters ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ")
     * @param length is value of expected string size
     * @return string with random symbols
     */
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * Method which gives opportunity to get user choice by entered integer value in console
     * @return entered integer value from console
     */
    public int getUserChoice(){
        return scanner.hasNextInt() ? scanner.nextInt() : WRONG_INPUT;
    }

    /**
     * Method which gives opportunity to get user String by entered symbols value in console
     * @return entered String value from console
     */
    public String getUserString(){
        return scanner.hasNextLine() ? scanner.nextLine() : "Default";
    }
    /**
     * Method which gives opportunity to get user Integer by entered integer value in console
     * @return entered Integer value from console
     */
    public int getUserInteger(){
        return scanner.hasNextInt() ? scanner.nextInt() : WRONG_INPUT;
    }


    /**
     * Method which simply print main menu
     */
    private void printMainMenu(){
        printStream.println(
                """
                        
                        Enter number of  command you wand to execute:
                        1 - Add new Literature object to Shelf
                        2 - Delete  Literature object by index from Shelf
                        3 - Borrow  Literature object by index from Shelf
                        4 - Arrive  Literature object by index back to Shelf
                        5 - Print list of available Books sorted by parameter...
                        6 - Print list of available Magazines sorted by parameter...
                        7 - Save in file
                        8 - Deserialize
                        9 - Print current state of Shelf
                        0 - Exit""");
    }

    /**
     * Method which simply print menu items for sorting books
     */
    private void printMenuForBooksSorting(){
        printStream.println(
                """
                        Choose type of sorting:
                        1 - Sort by 'name' value
                        2 - Sort by 'author' value
                        3 - Sort by 'page number' value
                        4 - Sort by 'date' value
                        Press another key to return\s
                          """);
    }

    /**
     * Method which simply print menu items for sorting magazines
     */
    private void printMenuForMagazinesSorting(){
        printStream.println(
                """
                        Choose type of sorting:
                        1 - Sort by 'name' value
                        2 - Sort by 'page' value
                        Press another key to return\s""");
    }

    /**
     * Method which simply print menu items for adding literature obj
     */
    private void printMenuForAddingLiterature(){
        printStream.println(
                """
                         Choose type of literature you want to add:
                         1 - Magazine
                         2 - Book
                         3 - Random Magazine
                         4 - Random Book
                         Press another key to return\s""");
    }

    public void stop(){
        setPlay(false);
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

}

