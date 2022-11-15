package org.vshmaliukh.shelf.literature_items.book_item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vshmaliukh.BaseAppConfig;
import org.vshmaliukh.shelf.literature_items.Item;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Book class which gives ability to create objects
 */
@Data
public class Book extends Item {

    private String author;
    private Date issuanceDate;

    public Book(String name, int pagesNumber, boolean isBorrowed, String author, Date issuanceDate) {
        super(name, pagesNumber, isBorrowed);
        this.author = author;
        this.issuanceDate = issuanceDate;
    }

    /**
     * Constructor for creating Book object
     */
    @JsonCreator
    public Book(@JsonProperty("id") Integer id,
                @JsonProperty("name") String name,
                @JsonProperty("pagesNumber") int pagesNumber,
                @JsonProperty("isBorrowed") boolean isBorrowed,
                @JsonProperty("author") String author,
                @JsonProperty("issuanceDate") Date issuanceDate) {
        super(id, name, pagesNumber, isBorrowed);
        this.author = author;
        this.issuanceDate = issuanceDate;
    }

    /**
     * Simple forming String about Book object
     *
     * @return String about Book object
     */
    @Override
    public String toString() {
        return "Book {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", author='" + author + '\'' +
                ", issuanceDate=" + new SimpleDateFormat(BaseAppConfig.DATE_FORMAT_STR).format(issuanceDate) +
                ", isBorrowed=" + borrowed +
                " }";
    }
}
