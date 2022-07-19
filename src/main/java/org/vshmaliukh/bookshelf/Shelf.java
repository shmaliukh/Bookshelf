package org.vshmaliukh.bookshelf;

import org.vshmaliukh.bookshelf.actionsWithShelf.BaseActionsWithShelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
public class Shelf implements BaseActionsWithShelf{

    private PrintWriter printWriter;

    private List<Item> itemInShelf;
    private List<Item> itemOutShelf;

    private Shelf(){
        itemInShelf = new ArrayList<>();
        itemOutShelf = new ArrayList<>();
    }

    public Shelf(PrintWriter printWriter){
        this();
        this.printWriter = printWriter;
    }

    public Shelf(List<Item> itemList, PrintWriter printWriter){
        this(printWriter);
        for (Item item : itemList) {
            this.addLiteratureObject(item);
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
    public void addLiteratureObject(Item item) {
        if(item != null){
            if(item.isBorrowed()){
                getLiteratureOutShelf().add(item);
            }
            else{
                getLiteratureInShelf().add(item);
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
    private void informAboutActionWithLiterature(Item item, String message) {
        printWriter.println(item + " " + message);
    }

    @Override
    public void borrowLiteratureObjectFromShelfByIndex(int index) {
        Item buffer;
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
        Item buffer;
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

        arrBooks.addAll(this.itemInShelf.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .collect(Collectors.toList()));

        arrBooks.addAll(this.itemOutShelf.stream()
                        .filter(Book.class::isInstance)
                        .map(Book.class::cast)
                        .collect(Collectors.toList()));
        return arrBooks;
    }

    public List<Magazine> getMagazines() {
        List <Magazine> arrMagazines = new ArrayList<>();

        arrMagazines.addAll(this.itemInShelf.stream()
                .filter(Magazine.class::isInstance)
                .map(Magazine.class::cast)
                .collect(Collectors.toList()));

        arrMagazines.addAll(this.itemOutShelf.stream()
                .filter(Magazine.class::isInstance)
                .map(Magazine.class::cast)
                .collect(Collectors.toList()));
        return arrMagazines;
    }

    public List<Item> getLiteratureInShelf() {
        return itemInShelf;
    }

    public List<Item> getLiteratureOutShelf() {
        return itemOutShelf;
    }

    public List<Item> getAllLiteratureObjects(){
        ArrayList<Item> itemArrayList = new ArrayList<>();
        itemArrayList.addAll(getBooks());
        itemArrayList.addAll(getMagazines());
        return itemArrayList;
    }

    /**
     * Simple forming String about Book object
     * @return String about Shelf object
     */
    @Override
    public String toString() {
        return  "Shelf {" +
                "\n\tliteratureInShelf=" + itemInShelf.toString() +
                "\n\tliteratureOutShelf=" + itemOutShelf.toString() +
                "}";
    }
}
