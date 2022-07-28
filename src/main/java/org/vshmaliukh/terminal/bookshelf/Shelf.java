package org.vshmaliukh.terminal.bookshelf;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
@Slf4j
public class Shelf {

    private final PrintWriter printWriter;
    private final List<Item> itemsOfShelf = new ArrayList<>();

    public Shelf(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public Shelf(List<Item> itemList, PrintWriter printWriter) {
        this(printWriter);
        itemList.forEach(this::addLiteratureObject);
    }

    public void addLiteratureObject(Item item) {
        if (item != null) {
            itemsOfShelf.add(item);
        } else {
            log.error("The literature item to add is null");
        }
    }

    public void deleteLiteratureObjectByIndex(int index) {
        if (!this.getLiteratureInShelf().isEmpty()) {
            if (index > 0 && index <= itemsOfShelf.size()) {
                informAboutActionWithLiterature(itemsOfShelf.remove(index - 1), "has deleted from shelf");
            } else {
                printWriter.println("Wrong index");
            }
        } else printWriter.println("Empty shelf");
    }

    /**
     * Method simply inform user about deleted Literature object
     */
    private void informAboutActionWithLiterature(Item item, String message) {
        printWriter.println(item + " " + message);
    }

    public void borrowLiteratureObjectFromShelfByIndex(int index) {
        changeStateOfLiteratureItem(this.getLiteratureInShelf(), index, true, "has borrowed from shelf");
    }

    public void arriveLiteratureObjectFromShelfByIndex(int index) {
        changeStateOfLiteratureItem(this.getLiteratureOutShelf(), index, false, "has arriver back to shelf");
    }

    private void changeStateOfLiteratureItem(List<Item> literatureList, int index, boolean stateBorrowed, String messageAboutAction) {
        if (!literatureList.isEmpty()) {
            if (index > 0 && index <= literatureList.size()) {
                Item buffer = literatureList.get(index - 1);
                buffer.setBorrowed(stateBorrowed);
                informAboutActionWithLiterature(buffer, messageAboutAction);
            } else {
                printWriter.println("Wrong index");
            }
        } else printWriter.println("No available literature");
    }

    public List<Item> getLiteratureInShelf() {
        return itemsOfShelf.stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    public List<Item> getLiteratureOutShelf() {
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
                System.lineSeparator() + "literatureInShelf=" + getLiteratureInShelf().toString() +
                System.lineSeparator() + "literatureOutShelf=" + getLiteratureOutShelf().toString() +
                "}";
    }
}
