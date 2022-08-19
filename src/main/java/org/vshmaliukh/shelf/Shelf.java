package org.vshmaliukh.shelf;

import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;

public class Shelf {

    protected List<Item> itemsOfShelf;

    public Shelf() {
        itemsOfShelf = new ArrayList<>();
    }

    public List<Item> getAllLiteratureObjects() {
        return new ArrayList<>(itemsOfShelf);
    }

    public List<Item> getItemsOfShelf() {
        return itemsOfShelf;
    }

    public void setItemsOfShelf(List<Item> itemsOfShelf) {
        this.itemsOfShelf = itemsOfShelf;
    }

    @Override
    public String toString() {
        return "Shelf {" + System.lineSeparator() +
                "literatureOfShelf=" + getAllLiteratureObjects().toString() + System.lineSeparator() +
                "}";
    }
}
