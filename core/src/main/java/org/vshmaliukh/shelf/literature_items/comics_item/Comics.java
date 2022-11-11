package org.vshmaliukh.shelf.literature_items.comics_item;

import lombok.Data;
import org.vshmaliukh.shelf.literature_items.Item;

@Data
public class Comics extends Item {

    private String publisher;

    public Comics(Integer id, String name, int pagesNumber, boolean isBorrowed, String publisher) {
        super(id, name, pagesNumber, isBorrowed);
        this.publisher = publisher;
    }

    public Comics(String name, int pagesNumber, boolean isBorrowed, String publisher) {
        super(name, pagesNumber, isBorrowed);
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
