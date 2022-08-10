package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.util.List;

public class WebShelfHandler extends AbstractShelfHandler {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
        this.shelf = new Shelf();
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!shelf.itemsOfShelf.isEmpty() && index > 0 && index <= shelf.itemsOfShelf.size()) {
            shelf.itemsOfShelf.remove(index - 1);
        }
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        if (!literatureList.isEmpty() && index > 0 && index <= literatureList.size()) {
            Item buffer = literatureList.get(index - 1);
            boolean borrowed = !buffer.isBorrowed();
            buffer.setBorrowed(borrowed);

        }
    }
}
