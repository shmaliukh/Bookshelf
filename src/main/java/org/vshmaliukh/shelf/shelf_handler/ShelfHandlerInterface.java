package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface ShelfHandlerInterface {

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd"; // todo create file with config (?)
    public static final String HOME_PROPERTY = System.getProperty("user.home");

    List<Item> readLiteratureInShelf();

    List<Item> readLiteratureOutShelf();

    void addLiteratureObject(Item item);

    void deleteLiteratureObjectByIndex(int index);

    void changeBorrowedStateOfItem(List<Item> literatureList, int index);

    void saveShelfItems();

    void readShelfItems();

    Shelf getShelf();
}
