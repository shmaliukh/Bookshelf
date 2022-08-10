package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.util.List;

public class SqlShelfHandler extends AbstractShelfHandler {

    public SqlShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
        this.shelf = new Shelf();
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {

    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {

    }
}
