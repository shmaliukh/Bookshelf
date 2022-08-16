package org.vshmaliukh.shelf;

import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;

public class Shelf {

    public List<Item> itemsOfShelf = new ArrayList<>();

    public Shelf() {
    }

    public List<Item> getAllLiteratureObjects() {
        return new ArrayList<>(itemsOfShelf);
    }

    @Override
    public String toString() {
        return "Shelf {" + System.lineSeparator() +
                "literatureOfShelf=" + getAllLiteratureObjects().toString() + System.lineSeparator() +
                "}";
    }
}
