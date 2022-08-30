package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractShelfHandler implements ShelfHandlerInterface {

    protected Shelf shelf;

    public AbstractShelfHandler() {
        this.shelf = new Shelf();
    }

    @Override
    public List<Item> readLiteratureInShelf() {
        return getShelf().getItemsOfShelf().stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return getShelf().getItemsOfShelf().stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            shelf.getItemsOfShelf().add(item);
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        if (!shelf.getItemsOfShelf().isEmpty()) {
            if (index > 0 && index <= shelf.getItemsOfShelf().size()) {
                shelf.getItemsOfShelf().remove(index - 1);
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

    public Shelf getShelf() {
        return shelf;
    }
}