package org.vshmaliukh.bookshelf;

import org.vshmaliukh.bookshelf.actionsWithShelf.BaseActionsWithShelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.constants.ConstantsForTerminal.*;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
public class Shelf implements BaseActionsWithShelf{

    private PrintWriter printWriter;

    private List<Literature> literatureInShelf;
    private List<Literature> literatureOutShelf;

    private List<List<Object>> allLiterature = new ArrayList<>(); // TODO delete when new GsonHandler is ready

    private Shelf(){
        literatureInShelf  = new ArrayList<>();
        literatureOutShelf = new ArrayList<>();
    }

    public Shelf(PrintWriter printWriter){
        this();
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

    public void printSortedMagazines(int typeOfSorting) {
        List<Magazine> magazineList = new ArrayList<>();
        switch (typeOfSorting){
            case SORT_MAGAZINES_BY_NAME:
                magazineList = getSortedMagazinesByName();
                break;
            case SORT_MAGAZINES_BY_PAGES_NUMBER:
                magazineList = getSortedMagazinesByPages();
                break;
            default:
                break;
        }
        for (Magazine o : magazineList) {
            printWriter.print(o);
        }// TODO make new printing method
    }

    public List<Magazine> getSortedMagazinesByName() {
        return getMagazines().stream()
                .sorted(Comparator.comparing(o -> o.getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Magazine> getSortedMagazinesByPages() {
        return getMagazines().stream()
                .sorted(Comparator.comparing(Magazine::getPagesNumber))
                .collect(Collectors.toList());
    }

    public void printSortedBooks(int typeOfSorting){
        List<Book> bookList = new ArrayList<>();
        switch (typeOfSorting){
            case SORT_BOOKS_BY_NAME:
                bookList = getSortedBooksByName();
                break;
            case SORT_BOOKS_BY_PAGES_NUMBER:
                bookList = getSortedBooksByPages();
                break;
            case SORT_BOOKS_BY_AUTHOR:
                bookList = getSortedBooksByAuthor();
                break;
            case SORT_BOOKS_BY_DATE_OF_ISSUE:
                bookList = getSortedBooksByDate();
                break;
            default:
                break;
        }
        for (Book o : bookList) {
            printWriter.print(o);
        } // TODO make new printing method
    }

    public List<Book> getSortedBooksByName() {
        return getBooks().stream()
                .sorted(Comparator.comparing(o -> o.getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getSortedBooksByPages() {
        return getBooks().stream()
                .sorted(Comparator.comparing(Book::getPagesNumber))
                .collect(Collectors.toList());
    }

    public List<Book> getSortedBooksByAuthor() {
        return getBooks().stream()
                .sorted(Comparator.comparing(o -> o.getAuthor().toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getSortedBooksByDate() {
        return getBooks().stream()
                .sorted(Comparator.comparing(o -> o.getIssuanceDate().getTime()))
                .collect(Collectors.toList());
    }

    public List<Book> getBooks() {
        List <Book> arrBooks = new ArrayList<>();

        arrBooks.addAll(this.literatureInShelf.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .collect(Collectors.toList()));

        arrBooks.addAll(this.literatureOutShelf.stream()
                        .filter(Book.class::isInstance)
                        .map(Book.class::cast)
                        .collect(Collectors.toList()));
        return arrBooks;
    }

    public List<Magazine> getMagazines() {
        List <Magazine> arrMagazines = new ArrayList<>();

        arrMagazines.addAll(this.literatureInShelf.stream()
                .filter(Magazine.class::isInstance)
                .map(Magazine.class::cast)
                .collect(Collectors.toList()));

        arrMagazines.addAll(this.literatureOutShelf.stream()
                .filter(Magazine.class::isInstance)
                .map(Magazine.class::cast)
                .collect(Collectors.toList()));
        return arrMagazines;
    }



    public List<Literature> getLiteratureInShelf() {
        return literatureInShelf;
    }

    public List<Literature> getLiteratureOutShelf() {
        return literatureOutShelf;
    }

    public List<Literature> getAllLiteratureObjects(){
        ArrayList<Literature> literatureArrayList = new ArrayList<>();
        literatureArrayList.addAll(getBooks());
        literatureArrayList.addAll(getMagazines());
        return literatureArrayList;
    }

    /**
     * Simple forming String about Book object
     * @return String about Shelf object
     */
    @Override
    public String toString() {
        return  "Shelf {" +
                "\n\tliteratureInShelf=" + literatureInShelf.toString() +
                "\n\tliteratureOutShelf=" + literatureOutShelf.toString() +
                "}";
    }

    public void printPrettyTable(List<Literature> listToPrint){
        int spaceForName = 4;
        int spaceForPages = 5;
        int spaceForIsBorrowed = 8;
        int spaceForAuthor = 6;
        int spaceForDate = 4;
        int gotNameLength = 0;
        int gotPagesLength = 0;
        int gotAuthorLength = 0;
        int gotForDateLength = 0;

        for (Magazine magazine : getMagazines()) {
            gotNameLength = magazine.getName().length();
            gotPagesLength = String.valueOf(magazine.getPagesNumber()).length();
            if(gotNameLength > spaceForName){
                spaceForName = gotNameLength;
            }
            if(gotPagesLength > spaceForPages){
                spaceForPages = gotPagesLength;
            }
        }
        for (Book book : getBooks()) {
            gotAuthorLength = book.getAuthor().length();
            gotForDateLength = book.getIssuanceDate().toString().length();
            if(gotAuthorLength > spaceForAuthor){
                spaceForAuthor = gotAuthorLength;
            }
            if(gotForDateLength > spaceForPages){
                spaceForDate = gotForDateLength;
            }
        }
        final String format = "| %-"
                + spaceForName+"s | %-"
                + spaceForPages +"s | %-"
                + spaceForIsBorrowed+"s | %-"
                + spaceForAuthor +"s | %-"
                + spaceForDate +"s |\n";

        printWriter.printf(format, "Name", "Pages", "Borrowed", "Author", "Date");
        for (Book book : getBooks()) {
            printWriter.printf(format,
                    book.getName(),
                    book.getPagesNumber(),
                    book.isBorrowed(),
                    book.getAuthor(),
                    book.getIssuanceDate().toString());
        }
        for (Magazine magazine : getMagazines()) {
            printWriter.printf(format,
                    magazine.getName(),
                    magazine.getPagesNumber(),
                    magazine.isBorrowed(),
                    "",
                    "");
        }
    }
}
