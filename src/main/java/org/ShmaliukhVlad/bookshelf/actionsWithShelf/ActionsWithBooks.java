package org.ShmaliukhVlad.bookshelf.actionsWithShelf;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is interface which describes all methods Shelf have to do with Books
 */
public interface ActionsWithBooks {
    /**
     * This method print info about books in shelf sorted by name
     */
    void printSortedBooksByName();

    /**
     * This method print info about books in shelf sorted by pages
     */
    void printSortedBooksByPages();

    /**
     * This method print info about books in shelf sorted by author
     */
    void printSortedBooksByAuthor();

    /**
     * This method print info about books in shelf sorted by date
     */
    void printSortedBooksByDate();

}
