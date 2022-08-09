package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.AbstractShelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public class WebShelf extends AbstractShelf {

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!itemsOfShelf.isEmpty() && index > 0 && index <= itemsOfShelf.size()) {
            itemsOfShelf.remove(index - 1);
        }
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        if (!literatureList.isEmpty() && index > 0 && index <= literatureList.size()) {
            Item buffer = literatureList.get(index - 1);
            buffer.setBorrowed(!buffer.isBorrowed());

        }
    }
}
