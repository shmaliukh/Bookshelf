package org.vshmaliukh.bookshelf.bookshelfObjects;

import lombok.Data;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Magazine class which gives ability to create objects
 */
@Data
public class Magazine extends Literature {

    /**
     * Constructor for creating Magazine object
     */
    public Magazine(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    /**
     * Simple forming String about Magazine object
     * @return String about Magazine object
     */
    @Override
    public String toString() {
        return  "Magazine {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                " }";
    }
}
