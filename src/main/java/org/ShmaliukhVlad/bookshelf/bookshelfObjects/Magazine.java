package org.ShmaliukhVlad.bookshelf.bookshelfObjects;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Magazine class which gives ability to create objects
 */
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
        String tab2 = "\n\t\t";
        String tab3 = "\n\t\t\t";
        return  tab2 + "Magazine{" +
                tab3 + "name='" + name + '\'' +
                tab3 + "pagesNumber=" + pagesNumber +
                tab3 + "isBorrowed=" + isBorrowed +
                tab2 + '}';
    }
}
