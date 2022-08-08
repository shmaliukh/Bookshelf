package org.vshmaliukh.bookshelf;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractShelf {

    protected final List<Item> itemsOfShelf = new ArrayList<>();

    protected AbstractShelf() {
    }

    public void addLiteratureObject(Item item) {
        if (item != null) {
            itemsOfShelf.add(item);
        } else {
            log.error("The literature item to add is null");
        }
    }

    public abstract void deleteLiteratureObjectByIndex(int index);

    public abstract void changeBorrowedStateOfItem(List<Item> literatureList, int index);

    public List<Item> readLiteratureInShelf() {
        return itemsOfShelf.stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    public List<Item> readLiteratureOutShelf() {
        return itemsOfShelf.stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    public List<Item> getAllLiteratureObjects() {
        return new ArrayList<>(itemsOfShelf);
    }

    @Override
    public String toString() {
        return "Shelf {" +
                System.lineSeparator() + "literatureInShelf=" + readLiteratureInShelf().toString() +
                System.lineSeparator() + "literatureOutShelf=" + readLiteratureOutShelf().toString() +
                "}";
    }
}
