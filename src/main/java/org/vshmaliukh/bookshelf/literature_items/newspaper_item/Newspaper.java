package org.vshmaliukh.bookshelf.literature_items.newspaper_item;

import lombok.Data;
import org.vshmaliukh.bookshelf.literature_items.Item;

@Data
public class Newspaper extends Item {

    public Newspaper(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    @Override
    public String toString() {
        return "Newspaper {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                " }";
    }
}
