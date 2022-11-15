package org.vshmaliukh.shelf.literature_items.magazine_item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vshmaliukh.shelf.literature_items.Item;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Magazine class which gives ability to create objects
 */
@Data
public class Magazine extends Item {

    public Magazine(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    @JsonCreator
    public Magazine(@JsonProperty("id") Integer id,
                     @JsonProperty("name") String name,
                     @JsonProperty("pagesNumber") int pagesNumber,
                     @JsonProperty("isBorrowed") boolean isBorrowed) {
        super(id, name, pagesNumber, isBorrowed);
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
                ", isBorrowed=" + borrowed +
                " }";
    }
}
