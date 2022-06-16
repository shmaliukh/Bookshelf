package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.bookshelf.Shelf;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
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

    public Terminal(){
        scanner = new Scanner(System.in);
        shelf = new Shelf();
        play = true;
    }

    /**
     * Method simulates Terminal work like a real one
     */
    public void startWork(){
        System.out.println("Terminal START");

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
            case WRONG_INPUT -> System.out.println("Wrong input");
            default -> System.out.println("Wrong input");
        }
    }

    /**
     * Method print info Shelf and it's Literature objects
     */
    private void printCurrentStateOfShelf() {
        String tab = "\t";
        System.out.println("Current state of Shelf:");
        System.out.println("literature IN {");
        shelf.getLiteratureInShelf()
                .forEach(o -> System.out.print(tab + o.getPrintableLineOfLiteratureObject()));
        System.out.println("}");
        System.out.println("literature OUT {");
        shelf.getLiteratureOutShelf()
                .forEach(o -> System.out.print(tab + o.getPrintableLineOfLiteratureObject()));
        System.out.println("}");
    }

    /**
     * Method close current terminal
     */
    private void closeTerminal() {
        askUserIfNeedToSaveShelf();
        setPlay(false);
        System.out.println("Terminal STOP");
    }

    /**
     * Method gives a choice to save the current state of the shelf
     */
    private void askUserIfNeedToSaveShelf() {
        System.out.println("Do you want to record the current state of the shelf?");
        System.out.println("Enter '1' if you want to save");
        System.out.println("Press another key to exit without saving");
        if(getUserChoice() == SAVE_CURRENT_STATE_OF_SHELF){
            saveShelf();
        }
    }

    /**
     * Method deserializes all Literature objects from .out file save in current Shelf.
     */
    private void deserializeShelf() {
        System.out.println("If you need get saving from file '" + FILE_NAME + "'");
        System.out.println("you LOOSE ALL INFO about current SHELF");
        System.out.println("If you need to rewrite it enter '1'");
        System.out.println("Press another key to return");
        switch (getUserChoice()){
            case READ_FILE ->{
                try {
                    shelf.deserialize();
                    System.out.println("Deserialized");
                } catch (IOException e) {
                    //System.out.println("Serialization error");
                    System.err.println("Deserialization error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            default ->{
                System.out.println("Deserialization canceled");
            }
        }
        System.out.println();
        printCurrentStateOfShelf();
    }

    /**
     * Method serializes all Shelf's Literature objects in .out file.
     */
    private void saveShelf() {
        File file = new File(FILE_NAME);
        int userChoice = REWRITE_FILE;
        if(file.exists()){
            System.out.println("File " + FILE_NAME + " contains info about previous saving");
            System.out.println("If you need to rewrite it enter '1'");
            System.out.println("Press another key to return");
            userChoice =getUserChoice();
        }
        if(userChoice == REWRITE_FILE) {
            System.out.println("Shelf will be save as '" + FILE_NAME + "'");
            try {
                shelf.saveShelfToFile();
            } catch (IOException e) {
                System.out.println("Saving error");
                System.err.println("Serialization error");
                throw new RuntimeException(e);
            }
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
        System.out.println("Enter INDEX of Literature object to arrive one:");
        for (int i = 0; i < shelf.getLiteratureOutShelf().size(); i++) {
            System.out.print( (i+1) + " " +  shelf.getLiteratureOutShelf().get(i).getPrintableLineOfLiteratureObject());
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object from Shelf
     */
    private void printMenuForBorrowingLiterature() {
        System.out.println("Enter INDEX of Literature object to borrow one:");
        for (int i = 0; i < shelf.getLiteratureInShelf().size(); i++) {
            System.out.print( (i+1) + " " +  shelf.getLiteratureInShelf().get(i).getPrintableLineOfLiteratureObject());
        }
    }

    /**
     * Method print menu with necessary information when user needs to delete some Literature object in Shelf
     */
    private void printMenuForDeletingLiterature() {
        System.out.println("Enter INDEX of Literature object to delete one:");
        for (int i = 0; i < shelf.getLiteratureInShelf().size(); i++) {
            System.out.print( (i+1) + " " +  shelf.getLiteratureInShelf().get(i).getPrintableLineOfLiteratureObject());
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
    private Magazine getUserMagazine() {
        Magazine userMagazineToAdd;
        String name;
        int pages;
        boolean isBorrowed = true;

        System.out.println("Enter name:");
        name = getUserString();
        System.out.println("Enter number of pages:");
        pages = getUserInteger();
        System.out.println("Enter 1 if Magazine is NOT borrowed");
        System.out.println("Press another key to continue");
        if(getUserChoice() == 1){
            isBorrowed = false;
        }

        userMagazineToAdd = new Magazine(name, pages, isBorrowed);
        return userMagazineToAdd;
    }

    /**
     * Method give ability to create custom Book
     * @return user created Book
     */
    private Book getUserBook() {
        int pages;
        String name;
        boolean isBorrowed;
        Book userBookToAdd;
        isBorrowed = true;
        String author;
        LocalDate dateOfIssue;

        System.out.println("Enter name:");
        name = getUserString();
        System.out.println("Enter number of pages:");
        pages = getUserInteger();
        System.out.println("Enter 1 if Book is NOT borrowed");
        System.out.println("Press another key to continue");
        if(getUserChoice() == 1){
            isBorrowed = false;
        }
        System.out.println("Enter author:");
        author = getUserString();

        dateOfIssue = getUserDateOfIssue();

        userBookToAdd = new Book(name, pages, isBorrowed, author, dateOfIssue);
        return userBookToAdd;
    }

    /**
     * Method ask user to enter year, month and day of Literature issue
     * @return LocalDate of Literature object issue
     */
    private LocalDate getUserDateOfIssue() {
        int year = getYear();
        int month = getMonth();
        int day = getDay();
        return LocalDate.of(year,month,day);
    }

    /**
     * Method ask user to enter month of Literature issue
     * @return Int month of issue
     * between 1 and 12 (included)
     */
    private int getMonth() {
        System.out.println("Enter the month number of issue:");
        int input = getUserInteger();
        if(input >= 1 && input <= 12){
            return input;
        }
        System.out.println("Wrong input month (from 1 to 12) try again");
        return getMonth();
    }

    /**
     * Method ask user to enter day of Literature issue
     * @return Int day of issue
     * between 1 and 31 (included)
     */
    //ToDo date
    private int getDay() {
        System.out.println("Enter the number of month of issue:");
        int input = getUserInteger();
        if(input >= 1 && input <= 31){
            return input;
        }
        System.out.println("Wrong input number of day (from 1 to 12) try again");
        return getDay();
    }

    /**
     * Method ask user to enter year of Literature issue
     * @return Int year of issue
     * between 1 and Number of current year (included)
     */
    private int getYear() {
        System.out.println("Enter the year of issue:");
        int input = getUserInteger();
        if(input >= 1 && input <= LocalDate.now().getYear()){
            return input;
        }
        System.out.println("Wrong input year (from 1 to current year) try again");
        return getYear();
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
                LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70)))));
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
    private int getUserChoice(){
        scanner = new Scanner(System.in);
        return scanner.hasNextInt() ? scanner.nextInt() : WRONG_INPUT;
    }

    /**
     * Method which gives opportunity to get user String by entered symbols value in console
     * @return entered String value from console
     */
    private String getUserString(){
        scanner = new Scanner(System.in);
        return scanner.hasNextLine() ? scanner.nextLine() : "Default";
    }
    /**
     * Method which gives opportunity to get user Integer by entered integer value in console
     * @return entered Integer value from console
     */
    private int getUserInteger(){
        scanner = new Scanner(System.in);
        return scanner.hasNextInt() ? scanner.nextInt() : WRONG_INPUT;
    }


    /**
     * Method which simply print main menu
     */
    private void printMainMenu(){
        System.out.println(
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
        System.out.println(
                """
                        Choose type of sorting:
                        1 - Sort by 'name' value
                        2 - Sort by 'author' value
                        3 - Sort by 'page number' value
                        4 - Sort by 'date' value
                        Press another key to return\s""");
    }

    /**
     * Method which simply print menu items for sorting magazines
     */
    private void printMenuForMagazinesSorting(){
        System.out.println(
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
        System.out.println(
                """
                         Choose type of literature you want to add:
                         1 - Magazine
                         2 - Book
                         3 - Random Magazine
                         4 - Random Book
                         Press another key to return\s""");
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
