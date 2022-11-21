package org.vshmaliukh.shelf.literature_items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Literature class which is abstraction class for generating extended classes like Book and Magazine
 */
@Data
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value= Book.class, name = "Book"),
        @JsonSubTypes.Type(value= Magazine.class, name = "Magazine"),
        @JsonSubTypes.Type(value= Comics.class, name = "Comics"),
        @JsonSubTypes.Type(value= Newspaper.class, name = "Newspaper"),
})
public abstract class Item implements Serializable {

    protected Integer id = null;

    protected String name;
    protected int pagesNumber;
    protected boolean borrowed;

    private Item(){}

    @JsonCreator
    protected Item(@JsonProperty("id") Integer id,
                   @JsonProperty("name") String name,
                   @JsonProperty("pagesNumber") int pagesNumber,
                   @JsonProperty("borrowed") boolean borrowed) {
        this.id = id;
        this.name = name;
        setPagesNumber(pagesNumber);
        this.borrowed = borrowed;
    }

    /**
     * Base Constructor for creating Book and Magazine object
     */

    protected Item(String name, int pagesNumber, boolean borrowed) {
        this.name = name;
        setPagesNumber(pagesNumber);
        this.borrowed = borrowed;
    }

    /**
     * Method validate information about pages value
     *
     * @param pagesNumber must be greater than 0
     */
    public void setPagesNumber(int pagesNumber) {
        if (pagesNumber < 0) {
            pagesNumber = 1;
        }
        this.pagesNumber = pagesNumber;
    }
}
