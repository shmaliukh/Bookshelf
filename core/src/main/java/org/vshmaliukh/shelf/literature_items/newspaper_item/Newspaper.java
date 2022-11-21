package org.vshmaliukh.shelf.literature_items.newspaper_item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vshmaliukh.shelf.literature_items.Item;

@Data
public class Newspaper extends Item {

    public Newspaper(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    @JsonCreator
    public Newspaper(@JsonProperty("id") Integer id,
                     @JsonProperty("name") String name,
                     @JsonProperty("pagesNumber") int pagesNumber,
                     @JsonProperty("isBorrowed") boolean isBorrowed) {
        super(id, name, pagesNumber, isBorrowed);
    }

    @Override
    public String toString() {
        return "Newspaper {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + borrowed +
                " }";
    }
}
