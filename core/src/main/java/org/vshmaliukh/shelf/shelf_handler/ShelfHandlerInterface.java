package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface ShelfHandlerInterface {

    List<Item> readLiteratureInShelf();

    List<Item> readLiteratureOutShelf();

    void addItem(Item item);

    void deleteItemByIndex(int index);

    void changeBorrowedStateOfItem(List<Item> literatureList, int index);

    Shelf getShelf();
}
