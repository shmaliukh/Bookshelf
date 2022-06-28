package org.ShmaliukhVlad.bookshelf.bookshelfObjects;

import static org.ShmaliukhVlad.constants.ConstantValues.SORT_MAGAZINES_BY_NAME;
import static org.ShmaliukhVlad.constants.ConstantValues.SORT_MAGAZINES_BY_PAGES_NUMBER;

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
        return  "Magazine {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                " }\n";
    }

    /**
     * Simple forming String about Magazine object in one line with necessary configuration
     * @param typeOfLineConfig integer value of configuration we need
     * sortMagazinesByName -> first printed value of Literature object is 'Name'
     * sortMagazinesByPagesNumber -> first printed value of Literature object is 'pagesNumber'
     * default -> return toString() method
     */
    @Override
    public String getPrintableLineOfLiteratureObject(int typeOfLineConfig){
        switch (typeOfLineConfig) {
            case SORT_MAGAZINES_BY_NAME:
                return "Magazine {" +
                        " name='" + name + '\'' +
                        ",  pagesNumber=" + pagesNumber +
                        ",  isBorrowed=" + isBorrowed +
                        " }\n";
            case SORT_MAGAZINES_BY_PAGES_NUMBER:
                return "Magazine {" +
                        " pagesNumber=" + pagesNumber +
                        ",  name='" + name + '\'' +
                        ",  isBorrowed=" + isBorrowed +
                        " }\n";
            default:
                return toString();
        }
    }
}
