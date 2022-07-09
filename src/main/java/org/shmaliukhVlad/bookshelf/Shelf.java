package org.shmaliukhVlad.bookshelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.shmaliukhVlad.bookshelf.actionsWithShelf.ActionsWithBooks;
import org.shmaliukhVlad.bookshelf.actionsWithShelf.ActionsWithMagazines;
import org.shmaliukhVlad.bookshelf.actionsWithShelf.BaseActionsWithShelf;
import org.shmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.shmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.shmaliukhVlad.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.shmaliukhVlad.constants.ConstantValues.*;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
@Data
@AllArgsConstructor
public class Shelf implements BaseActionsWithShelf, ActionsWithBooks, ActionsWithMagazines {

    private PrintWriter printWriter;

    private List<Literature> literatureInShelf  = new ArrayList<>();
    private List<Literature> literatureOutShelf = new ArrayList<>();

    public List<List<Object>> allLiterature = new ArrayList<>();

    public Shelf(PrintWriter printWriter){
        this.printWriter = printWriter;
    }

    public Shelf(List<Literature> literatureList, PrintWriter printWriter){
        this(printWriter);
        for (Literature literature : literatureList) {
            this.addLiteratureObject(literature);
        }
    }

    public Shelf(List<Book> books, List<Magazine> magazines, PrintWriter printWriter) {
        this(printWriter);
        if(!books.isEmpty()){
            for (Book book : books) {
                this.addLiteratureObject(book);
            }
        }
        if(!magazines.isEmpty()){
            for (Magazine magazine : magazines) {
                this.addLiteratureObject(magazine);
            }
        }
    }

    /**
     * Method returns all available books which are inside (not borrowed) Shelf
     * @return list of Book objects inside Shelf
     */
    public List<Book> getAvailableBooks(){
        return this.getBooks().stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    /**
     * Method returns all available magazines which are inside (not borrowed) Shelf
     * @return list of Magazine objects inside Shelf
     */
    public List<Magazine> getAvailableMagazines(){
        return this.getMagazines().stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public void addLiteratureObject(Literature literature) {
        if(literature != null){
            if(literature.isBorrowed()){
                getLiteratureOutShelf().add(literature);
            }
            else{
                getLiteratureInShelf().add(literature);
            }
        }
        else {

            printWriter.println("The literature object (book or magazine) is empty");
            System.err.println("The literature object (book or magazine) is NULL");
        }
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if(!this.getLiteratureInShelf().isEmpty()){
            if(index >0 && index <= this.getLiteratureInShelf().size()){
                informAboutActionWithLiterature(this.getLiteratureInShelf().remove(index-1), "has deleted from shelf");
            }
            else {
                printWriter.println("Wrong index");
            }
        }
        else printWriter.println("Empty shelf");
    }

    /**
     * Method simply inform user about deleted Literature object
     */
    private void informAboutActionWithLiterature(Literature literature, String message) {
        printWriter.println(literature + " " + message);
    }

    @Override
    public void borrowLiteratureObjectFromShelfByIndex(int index) {
        Literature buffer;
        if(!this.getLiteratureInShelf().isEmpty()){
            if(index > 0 && index <= this.getLiteratureInShelf().size()){
                buffer = this.getLiteratureInShelf().remove(index-1);
                buffer.setBorrowed(true);
                this.getLiteratureOutShelf().add(buffer);
                informAboutActionWithLiterature(buffer, "has borrowed from shelf");
            }
            else {
                printWriter.println("Wrong index");
            }
        }
        else printWriter.println("No available literature");
    }

    @Override
    public void arriveLiteratureObjectFromShelfByIndex(int index) {
        Literature buffer;
        if(!this.getLiteratureOutShelf().isEmpty()){
            if(index > 0 && index <= this.getLiteratureOutShelf().size()){
                buffer = this.getLiteratureOutShelf().remove(index-1);
                buffer.setBorrowed(false);
                this.getLiteratureInShelf().add(buffer);
                informAboutActionWithLiterature(buffer, "has arrived back to shelf");
            }
            else {
                printWriter.println("Wrong index");
            }
        }
        else printWriter.println("Literature is not borrowed");
    }

    public void printSortedMagazines(int typeOfSorting) { // TODO create new realization
        List<Magazine> magazineList = getMagazines();
        if(magazineList.isEmpty()){
            printWriter.println("No available magazines in Shelf");
        }
        else {
            switch (typeOfSorting){
                case SORT_MAGAZINES_BY_NAME:
                    magazineList.stream()
                            .sorted(Comparator.comparing(o -> o.getName().toLowerCase()))
                            .collect(Collectors.toList());
                    break;
                case SORT_MAGAZINES_BY_PAGES_NUMBER:
                    magazineList.stream()
                            .sorted(Comparator.comparing(Magazine::getPagesNumber))
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
            magazineList.forEach(o -> printWriter.println(o));
        }


        //if(getSortedMagazinesByName().isEmpty()){
        //    printWriter.println("No available magazines in Shelf");
        //}
        //else {
        //    printWriter.println("Available magazines sorted by name:");
        //    //printWriter.println(getAllLiterature());
        //    for (Magazine magazine : getSortedMagazinesByName()) {
        //        //printWriter.printf();
        //        printWriter.print(magazine.getPrintableLineOfLiteratureObject(SORT_MAGAZINES_BY_NAME));
        //    }
        //}
    }

    @Override
    public void printSortedMagazinesByName() {
        if(getSortedMagazinesByName().isEmpty()){
            printWriter.println("No available magazines in Shelf");
        }
        else {
            printWriter.println("Available magazines sorted by name:");
            //printWriter.println(getAllLiterature());
            for (Magazine magazine : getSortedMagazinesByName()) {
                //printWriter.printf();
                printWriter.print(magazine.getPrintableLineOfLiteratureObject(SORT_MAGAZINES_BY_NAME));
            }
        }
    }

    @Override
    public void printSortedMagazinesByPages() {
        if(getSortedMagazinesByPages().isEmpty()){
            printWriter.println("No available magazines in Shelf");
        }
        else {
            printWriter.println("Available magazines sorted by pages:");
            for (Magazine magazine : getSortedMagazinesByPages()) {
                printWriter.print(magazine.getPrintableLineOfLiteratureObject(SORT_MAGAZINES_BY_PAGES_NUMBER));
            }
        }
    }

    @Override
    public void printSortedBooksByName() {
        if(getSortedBooksByName().isEmpty()){
            printWriter.println("No available books in Shelf");
        }
        else {
            printWriter.println("Available books sorted by name:");
            for (Book book : getSortedBooksByName()) {
                printWriter.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_NAME));
            }
        }
    }

    @Override
    public void printSortedBooksByPages() {
        if(getSortedBooksByPages().isEmpty()){
            printWriter.println("No available books in Shelf");
        }
        else {
            printWriter.println("Available books sorted by pages:");
            for (Book book : getSortedBooksByPages()) {
                printWriter.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_PAGES_NUMBER));
            }
        }
    }

    @Override
    public void printSortedBooksByAuthor() {
        if(getSortedBooksByAuthor().isEmpty()){
                 printWriter.println("No available books in Shelf");
        }
        else {
            printWriter.println("Available books sorted by author:");
            for (Book book : getSortedBooksByAuthor()) {
                        printWriter.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_AUTHOR));
            }
        }
    }

    @Override
    public void printSortedBooksByDate() {
        if(getSortedBooksByDate().isEmpty()){
            printWriter.println("No available books in Shelf");
        }
        else {
            printWriter.println("Available books sorted by date of issue:");
            for (Book book : getSortedBooksByDate()) {
                printWriter.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_DATE_OF_ISSUE));
            }
        }
    }

    /**
     * Method returns sorted ArrayList of Magazines by Name number from current Shelf
     * @return ArrayList<Magazine>
     *     firstly sorted by Name
     *     than sorted by Pages number
     */
    public ArrayList<Magazine> getSortedMagazinesByName(){
        return (ArrayList<Magazine>) this.getLiteratureInShelf().stream()
                .filter((Literature o) -> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .sorted(Comparator.comparing(
                                o -> ((Magazine) o).getName().toLowerCase())
                        .thenComparing(
                                o -> ((Magazine) o).getPagesNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Magazines by Pages number from current Shelf
     * @return ArrayList<Magazine>
     *     firstly sorted by Pages number
     *     than sorted by Name
     */
    public ArrayList<Magazine> getSortedMagazinesByPages(){
        return (ArrayList<Magazine>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .sorted(Comparator.comparing(
                                o -> ((Magazine) o).getPagesNumber())
                        .thenComparing(
                                o -> ((Magazine) o).getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Name from current Shelf
     * @return ArrayList<Book>
     *     firstly sorted by Name
     *     than sorted by Pages number
     */
    public ArrayList<Book> getSortedBooksByName(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparing(
                                o -> ((Book) o).getName().toLowerCase())
                        .thenComparing(
                                o -> ((Book) o).getPagesNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Pages number from current Shelf
     * @return ArrayList<Book>
     *     sorted by Pages number
     */
    public ArrayList<Book> getSortedBooksByPages(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparing(Book::getPagesNumber))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Author from current Shelf
     * @return ArrayList<Book>
     *     sorted by Author
     */
    public ArrayList<Book> getSortedBooksByAuthor(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparing(
                            o -> o.getAuthor().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Date from current Shelf
     * @return ArrayList<Book>
     *     sorted by Date
     */
    public ArrayList<Book> getSortedBooksByDate(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparingLong(
                                o -> o.getIssuanceDate().getTime()))
                .collect(Collectors.toList());
    }

    public List<Book> getBooks() {
        List <Book> arrBooks = new ArrayList<>();

        arrBooks.addAll(this.literatureInShelf.stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .collect(Collectors.toList()));

        arrBooks.addAll(this.literatureOutShelf.stream()
                        .filter((Literature o)-> o instanceof Book)
                        .map(o -> (Book) o)
                        .collect(Collectors.toList()));
        return arrBooks;
    }

    public List<Magazine> getMagazines() {
        List <Magazine> arrMagazines = new ArrayList<>();

        arrMagazines.addAll(this.literatureInShelf.stream()
                .filter((Literature o)-> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .collect(Collectors.toList()));

        arrMagazines.addAll(this.literatureOutShelf.stream()
                .filter((Literature o)-> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .collect(Collectors.toList()));
        return arrMagazines;
    }


    /**
     * Simple forming String about Book object
     * @return String about Shelf object
     */
    @Override
    public String toString() {
        return  "Shelf {" +
                "\n\tliteratureInShelf=" + literatureInShelf +
                "\n\tliteratureOutShelf=" + literatureOutShelf +
                "}";
    }
}
