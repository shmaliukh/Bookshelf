package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.bookshelf.Shelf;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


/**
 * Class which gives user interactive interface
 */
public class Terminal {
    private boolean play;

    private Shelf shelf;
    private Scanner scanner;

    public Terminal(){
        scanner = new Scanner(System.in);
        shelf = new Shelf();
        play = true;
    }

    private static final int addNewLiterature = 1;
    private static final int deleteLiterature = 2;
    private static final int borrowLiterature = 3;
    private static final int arriveLiterature = 4;
    private static final int printSortedBooks = 5;
    private static final int printSortedMagazines = 6;
    private static final int saveShelfInFile = 7;
    private static final int deserialize = 8;
    private static final int printShelf = 9;
    private static final int exit = 0;

    private static final int addCustomMagazine = 1;
    private static final int addCustomBook = 2;
    private static final int addRandomMagazine = 3;
    private static final int addRandomBook = 4;

    private static final int sortBooksByName = 1;
    private static final int sortBooksByPagesNumber = 2;
    private static final int sortBooksByAuthor = 3;
    private static final int sortBooksByDateOfIssue = 4;

    private static final int sortMagazinesByName = 1;
    private static final int sortMagazinesByPagesNumber = 2;

    /**
     * Method simulates Terminal work like a real one
     */
    public void startWork(){
        System.out.println("Terminal START");

        while (isPlay()){
            generateUserInterface();
        }

        System.out.println("Terminal STOP");
    }

    /**
     * Method which gives opportunity to choose command by entering number of item to execute
     * Continually print menu items and wait for user choice
     * Works till user enter '0' (Exit)
     */
    private void generateUserInterface() {
        printMainMenu();

        switch (getUserChoice()){
            case addNewLiterature:
                printMenuForAddingLiterature();//ToDo
                chooseTypeAndAddIt();
                break;
            case deleteLiterature:
                printMenuForDeletingLiterature();
                shelf.deleteLiteratureObjectByIndex(getUserChoice());//ToDo
                break;
            case borrowLiterature:
                printMenuForBorrowingLiterature();
                shelf.borrowLiteratureObjectFromShelfByIndex(getUserChoice());//ToDo
                break;
            case arriveLiterature:
                printMenuForArrivingLiterature();
                shelf.arriveLiteratureObjectFromShelfByIndex(getUserChoice());//ToDo
                break;
            case printSortedBooks:
                clarificationForSortingBooks();
                break;
            case printSortedMagazines:
                clarificationForSortingMagazines();
                break;
            case saveShelfInFile:
                saveShelf();
                break;
            case deserialize:
                deserializeShelf();
                break;
            case printShelf:
                System.out.println(shelf);
                break;
            case exit:
                setPlay(false);
                break;
            default:
                break;
        }
    }

    /**
     * Method deserializes all Literature objects from .out file save in current Shelf.
     */
    private void deserializeShelf() {// ToDo
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

    /**
     * Method serializes all Shelf's Literature objects in .out file.
     */
    private void saveShelf() {// ToDo
        try {
            shelf.saveShelfToFile();
            System.out.println("Serialized");
        } catch (IOException e) {
            //System.out.println("Serialization error");
            System.err.println("Serialization error");
            throw new RuntimeException(e);
        }
    }

    /**
     * Method gives ability to choose method for sorting Magazines and print sorted list
     */
    private void clarificationForSortingMagazines() {
        printMenuForMagazinesSorting();
        switch (getUserChoice()){
            case sortMagazinesByName:
                System.out.println("Sorted by name");
                shelf.printSortedMagazinesByName();
                break;
            case sortMagazinesByPagesNumber:
                System.out.println("Sorted by pages");
                shelf.printSortedMagazinesByPages();
                break;
            default:
                break;
        }
    }

    /**
     * Method gives ability to choose method for sorting Books and print sorted list
     */
    private void clarificationForSortingBooks() {
        printMenuForBooksSorting();
        switch (getUserChoice()){
            case sortBooksByName:
                System.out.println("Sorted by name");
                shelf.printSortedBooksByName();
                break;
            case sortBooksByAuthor:
                System.out.println("Sorted by author");
                shelf.printSortedBooksByAuthor();
                break;
            case sortBooksByPagesNumber:
                System.out.println("Sorted by pages");
                shelf.printSortedBooksByPages();
                break;
            case sortBooksByDateOfIssue:
                System.out.println("Sorted by date of issue");
                shelf.printSortedBooksByDate();
                break;
            default:
                break;
        }
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object back to Shelf
     */
    private void printMenuForArrivingLiterature() {
        System.out.println("Enter index of Literature object to arrive one:");
        System.out.println(shelf.getLiteratureOutShelf());
    }

    /**
     * Method print menu with necessary information when user needs to borrow some Literature object from Shelf
     */
    private void printMenuForBorrowingLiterature() {
        System.out.println("Enter index of Literature object to borrow one:");
        System.out.println(shelf.getLiteratureInShelf());
    }

    /**
     * Method print menu with necessary information when user needs to delete some Literature object in Shelf
     */
    private void printMenuForDeletingLiterature() {
        System.out.println("Enter INDEX of Literature object to delete one:");
        //Todo
        System.out.println(shelf);
    }


    /**
     * Method give user ability to choose new Literature object type
     * generate new Literature object (Magazine or Book) with user or random parameters (depends on user choice)
     */
    private void chooseTypeAndAddIt() {
        switch (getUserChoice()){
            
            case addCustomMagazine:
                Magazine userMagazineToAdd;
                String name = "";
                int pages = 0;
                boolean isBorrowed = false;

                System.out.println("Enter name:");
                name = scanner.next();
                System.out.println("Enter number of pages:");
                pages = scanner.nextInt();
                System.out.println("Enter 1 if Magazine is NOT borrowed");
                System.out.println("Press another key to continue");
                if(getUserChoice() == 1){
                    isBorrowed = false;
                }

                userMagazineToAdd = new Magazine(name, pages, isBorrowed);
                shelf.addLiteratureObject(userMagazineToAdd);
                System.out.println("New user Magazine has added to shelf");
                break;
                
            case addCustomBook:
                Book userBookToAdd;
                isBorrowed = false;
                String author = "";
                LocalDate dateOfIssue;

                System.out.println("Enter name:");
                name = scanner.next();
                System.out.println("Enter number of pages:");
                pages = scanner.nextInt();
                System.out.println("Enter 1 if Book is NOT borrowed");
                System.out.println("Press another key to continue");
                if(getUserChoice() == 1){
                    isBorrowed = false;
                }
                System.out.println("Enter author:");
                author = scanner.next();

                System.out.println("Enter date of issue (format \"dd-MMM-yyyy\"):");
                int day = scanner.nextInt();
                int year = scanner.nextInt();
                int month = scanner.nextInt();
                dateOfIssue = LocalDate.of(year, month, day);

                userBookToAdd = new Book(name, pages, isBorrowed, author, dateOfIssue);
                shelf.addLiteratureObject(userBookToAdd);
                System.out.println("New user Book has added to shelf");
                break;
                
            case addRandomMagazine:
                Magazine randomMagazine = getRandomMagazine();
                shelf.addLiteratureObject(randomMagazine);
                System.out.println("New random Magazine has added to shelf");
                break;
                
            case addRandomBook:
                Book randomBook = getRandomBook();
                shelf.addLiteratureObject(randomBook);
                System.out.println("New random Book has added to shelf");
                break;

            default:
                break;
        }
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
     * @return entered integer value in console
     */
    private int getUserChoice(){
        return scanner.nextInt();
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
                         "1 - Magazine
                         "2 - Book
                         "3 - Random Magazine
                         "4 - Random Book
                         "Press another key to return\s""");
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
