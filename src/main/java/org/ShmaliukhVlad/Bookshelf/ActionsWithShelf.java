package org.ShmaliukhVlad.Bookshelf;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;

import java.io.IOException;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is interface which describes all methods Shelf have to do
 */
public interface ActionsWithShelf {

    /**
     * This method for adding Literature object (Book or Magazine) to the shelf
     * add element to the end of shelf objects
     * @param literature Book or Magazine object
     */
    default void addLiteratureObject(Literature literature){
    }

    /**
     * This method for deleting Literature object (Book or Magazine) from the shelf by index
     * @param index index of Literature object in shelf need to delete
     */
    default void deleteLiteratureObjectByIndex(int index){
    }

    /**
     * This method for borrowing Literature object (Book or Magazine) from the shelf by index
     * @param index index of Literature object in shelf need to borrow
     */
    default void borrowLiteratureObjectFromShelfByIndex(int index) {
    }

    /**
     * This method for arriving borrowed Literature object (Book or Magazine) back to the shelf by index
     * @param index index of Literature object out shelf need to arrive
     */
    default void arriveLiteratureObjectFromShelfByIndex(int index) {
    }

    /**
     * This method print info about magazines in shelf sorted by name
     */
    default void printSortedMagazinesByName(){
    }

    /**
     * This method print info about magazines in shelf sorted by pages
     */
    default void printSortedMagazinesByPages(){
    }

    /**
     * This method print info about books in shelf sorted by name
     */
    default void printSortedBooksByName(){
    }

    /**
     * This method print info about books in shelf sorted by pages
     */
    default void printSortedBooksByPages(){
    }

    /**
     * This method print info about books in shelf sorted by author
     */
    default void printSortedBooksByAuthor(){
    }

    /**
     * This method print info about books in shelf sorted by date
     */
    default void printSortedBooksByDate(){
    }

    /**
     * This method serialize all magazines and books in file
     * @throws IOException {@link IOException}
     */
    default void saveShelfToFile() throws IOException {
    }
}
