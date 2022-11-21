package org.vshmaliukh.shelf.literature_items.comics_item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vshmaliukh.shelf.literature_items.Item;

@Data
public class Comics extends Item {

    private String publisher;

    public Comics(String name, int pagesNumber, boolean isBorrowed, String publisher) {
        super(name, pagesNumber, isBorrowed);
        this.publisher = publisher;
    }

    @JsonCreator
    public Comics(@JsonProperty("id") Integer id,
                  @JsonProperty("name") String name,
                  @JsonProperty("pagesNumber") int pagesNumber,
                  @JsonProperty("isBorrowed") boolean isBorrowed,
                  @JsonProperty("publisher") String publisher) {
        super(id, name, pagesNumber, isBorrowed);
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Comics {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", publisher='" + publisher + '\'' +
                ", isBorrowed=" + borrowed +
                " }";
    }
}
