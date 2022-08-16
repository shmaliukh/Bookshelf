package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.AbstractSaveReadAbleShelfHandler;

import java.util.List;

public class WebShelfHandler extends AbstractSaveReadAbleShelfHandler {

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!shelf.itemsOfShelf.isEmpty()) {
            if (index > 0 && index <= shelf.itemsOfShelf.size()) {
                shelf.itemsOfShelf.remove(index - 1);
            }
        }
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        if (!literatureList.isEmpty()) {
            if (index > 0 && index <= literatureList.size()) {
                Item buffer = literatureList.get(index - 1);
                buffer.setBorrowed(!buffer.isBorrowed());
            }
        }
    }
}
