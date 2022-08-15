package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.ShelfHandlerInterface;

import java.util.List;

public class SqlLiteShelfHandler implements ShelfHandlerInterface {

    Shelf shelf = new Shelf();

    @Override
    public List<Item> readLiteratureInShelf() {

        return null;
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return null;
    }

    @Override
    public void addLiteratureObject(Item item) {

    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {

    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {

    }

    @Override
    public void saveShelfItems() {

    }

    @Override
    public void readShelfItems() {

    }

    @Override
    public Shelf getShelf() {
        return null;
    }
}
