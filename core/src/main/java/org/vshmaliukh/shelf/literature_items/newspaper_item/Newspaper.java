package org.vshmaliukh.shelf.literature_items.newspaper_item;

import lombok.Data;
import org.vshmaliukh.shelf.literature_items.Item;

@Data
public class Newspaper extends Item {

    public Newspaper(Integer id, String name, int pagesNumber, boolean isBorrowed) {
        super(id, name, pagesNumber, isBorrowed);
    }

    public Newspaper(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
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
