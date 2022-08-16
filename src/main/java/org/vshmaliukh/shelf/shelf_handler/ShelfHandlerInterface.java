package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface ShelfHandlerInterface {

    // todo create file with config (?)
    String DATE_FORMAT_STR = "yyyy-MM-dd";
    String HOME_PROPERTY = System.getProperty("user.home");

    List<Item> readLiteratureInShelf();

    List<Item> readLiteratureOutShelf();

    void addLiteratureObject(Item item);

    void deleteLiteratureObjectByIndex(int index);

    void changeBorrowedStateOfItem(List<Item> literatureList, int index);

    Shelf getShelf();
}
