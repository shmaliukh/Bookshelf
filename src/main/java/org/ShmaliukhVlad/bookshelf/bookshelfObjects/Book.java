package org.ShmaliukhVlad.bookshelf.bookshelfObjects;

import java.time.LocalDate;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Book class which gives ability to create objects
 */
public class Book extends Literature{

    private String author;
    private LocalDate issuanceDate;

    /**
     * Constructor for creating Book object
     */
    public Book(String name, int pagesNumber, boolean isBorrowed, String author, LocalDate issuanceDate) {
        super(name, pagesNumber, isBorrowed);
        this.author = author;
        this.issuanceDate = issuanceDate;
    }

    /**
     * Simple forming String about Book object
     * @return String about Magazine object
     */
    @Override
    public String toString() {
        String tab2 = "\n\t\t";
        String tab3 = "\n\t\t\t";
        return tab2 + "Book{" +
               tab3 + "author='" + author + '\'' +
               tab3 + "issuanceDate=" + issuanceDate +
               tab3 + "name='" + name + '\'' +
               tab3 + "pagesNumber=" + pagesNumber +
               tab3 + "isBorrowed=" + isBorrowed +
               tab2 + "}";
    }

    public String fegPrintableLineOfBook(){
        String tab2 = "\n\t\t";
        String tab3 = "\n\t\t\t";
        return tab2 + "Book{" +
                tab3 + "author='" + author + '\'' +
                tab3 + "issuanceDate=" + issuanceDate +
                tab3 + "name='" + name + '\'' +
                tab3 + "pagesNumber=" + pagesNumber +
                tab3 + "isBorrowed=" + isBorrowed +
                tab2 + "}";
    }
    //getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(LocalDate issuanceDate) {
        this.issuanceDate = issuanceDate;
    }
}
