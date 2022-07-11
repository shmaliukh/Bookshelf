package org.vshmaliukh.bookshelf.bookshelfObjects;

import lombok.Data;

import java.util.Date;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Book class which gives ability to create objects
 */
@Data
public class Book extends Literature{

    private String author;
    private Date issuanceDate;

    /**
     * Constructor for creating Book object
     */
    public Book(String name, int pagesNumber, boolean isBorrowed, String author, Date issuanceDate) {
        super(name, pagesNumber, isBorrowed);
        this.author = author;
        this.issuanceDate = issuanceDate;
    }

    /**
     * Simple forming String about Book object
     * @return String about Book object
     */
    @Override
    public String toString() {
        return "Book {" +
               " name='" + name + '\'' +
               ", pagesNumber=" + pagesNumber +
               ", author='" + author + '\'' +
               ", issuanceDate=" + issuanceDate +
               ", isBorrowed=" + isBorrowed +
               " }\n";
    }
}
