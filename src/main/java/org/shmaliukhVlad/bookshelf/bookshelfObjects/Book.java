package org.shmaliukhVlad.bookshelf.bookshelfObjects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

import static org.shmaliukhVlad.constants.ConstantValues.*;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Book class which gives ability to create objects
 */
@Data
public class Book extends Literature{

    @SerializedName("Author")
    private String author;
    @SerializedName("Date of issue")
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

    /**
     * Simple forming String about Book object in one line with necessary configuration
     * @param typeOfLineConfig integer value of configuration we need
     * @return String about Book object in one line.
     * sortBooksByName -> first value of Literature object is 'Name'
     * sortBooksByPagesNumber -> first value of Literature object is 'pagesNumber'
     * sortBooksByAuthor -> first value of Literature object is 'Author'
     * sortBooksByDateOfIssue -> first value of Literature object is 'issuanceDate'
     * sortBooksByDateOfIssue -> first value of Literature object is 'issuanceDate'
     * default -> return toString() method
     */
    @Override
    public String getPrintableLineOfLiteratureObject(int typeOfLineConfig){
        // FIXME simplify method
        switch (typeOfLineConfig) {
            case SORT_BOOKS_BY_NAME:
                return "Book {" +
                        " name='" + name + '\'' +
                        ",  pagesNumber=" + pagesNumber +
                        ",  author='" + author + '\'' +
                        ",  issuanceDate=" + issuanceDate +
                        ",  isBorrowed=" + isBorrowed +
                        " }\n";
            case SORT_BOOKS_BY_PAGES_NUMBER:
                return "Book {" +
                        " pagesNumber=" + pagesNumber +
                        ",  name='" + name + '\'' +
                        ",  author='" + author + '\'' +
                        ",  issuanceDate=" + issuanceDate +
                        ",  isBorrowed=" + isBorrowed +
                        " }\n";
            case SORT_BOOKS_BY_AUTHOR:
                return "Book {" +
                        " author='" + author + '\'' +
                        ",  name='" + name + '\'' +
                        ",  pagesNumber=" + pagesNumber +
                        ",  issuanceDate=" + issuanceDate +
                        ",  isBorrowed=" + isBorrowed +
                        " }\n";
            case SORT_BOOKS_BY_DATE_OF_ISSUE:
                return "Book {" +
                        " issuanceDate=" + issuanceDate +
                        ",  name='" + name + '\'' +
                        ",  author='" + author + '\'' +
                        ",  pagesNumber=" + pagesNumber +
                        ",  isBorrowed=" + isBorrowed +
                        " }\n";
            default:
                return toString();
        }
    }
}
