package org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item;

import lombok.Data;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Magazine class which gives ability to create objects
 */
@Data
public class Magazine extends Item {

    /**
     * Constructor for creating Magazine object
     */
    public Magazine(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    /**
     * Simple forming String about Magazine object
     *
     * @return String about Magazine object
     */
    @Override
    public String toString() {
        return "Magazine {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                " }";
    }
}
