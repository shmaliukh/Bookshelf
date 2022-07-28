package org.vshmaliukh.terminal.bookshelf.literature_items.comics_item;

import lombok.Data;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;

@Data
public class Comics extends Item {

    private String publisher;

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
                ", isBorrowed=" + isBorrowed +
                " }";
    }
}
