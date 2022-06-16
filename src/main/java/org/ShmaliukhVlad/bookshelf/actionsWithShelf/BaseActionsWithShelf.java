package org.ShmaliukhVlad.bookshelf.actionsWithShelf;

import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;

import java.io.IOException;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is interface which describes all base methods Shelf have to do
 */
public interface BaseActionsWithShelf {

    /**
     * This method for adding Literature object (Book or Magazine) to the shelf
     * add element to the end of shelf objects
     * @param literature Book or Magazine object
     */
    void addLiteratureObject(Literature literature);

    /**
     * This method for deleting Literature object (Book or Magazine) from the shelf by index
     * @param index index of Literature object in shelf need to delete
     */
    void deleteLiteratureObjectByIndex(int index);

    /**
     * This method for borrowing Literature object (Book or Magazine) from the shelf by index
     * @param index index of Literature object in shelf need to borrow
     */
    void borrowLiteratureObjectFromShelfByIndex(int index);

    /**
     * This method for arriving borrowed Literature object (Book or Magazine) back to the shelf by index
     * @param index index of Literature object out shelf need to arrive
     */
    void arriveLiteratureObjectFromShelfByIndex(int index);

    /**
     * This method serialize all magazines and books in file
     * @throws IOException {@link IOException}
     */
    void saveShelfToFile() throws IOException;

}
