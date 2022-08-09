package org.vshmaliukh.console_terminal_app;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.AbstractShelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.io.*;
import java.util.*;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
@Slf4j
public class ConsoleShelf extends AbstractShelf {

    private final PrintWriter printWriter;

    public ConsoleShelf(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!itemsOfShelf.isEmpty()) {
            if (index > 0 && index <= itemsOfShelf.size()) {
                printWriter.println(itemsOfShelf.remove(index - 1) + " " + "has deleted from shelf");
            } else {
                printWriter.println("Wrong index");
            }
        } else printWriter.println("Empty shelf");
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        if (!literatureList.isEmpty()) {
            if (index > 0 && index <= literatureList.size()) {
                Item buffer = literatureList.get(index - 1);
                buffer.setBorrowed(!buffer.isBorrowed());
                if (buffer.isBorrowed()) {
                    printWriter.println(buffer + " has borrowed from shelf");
                } else {
                    printWriter.println(buffer + " has arriver back to shelf");
                }
            } else {
                printWriter.println("Wrong index");
            }
        } else {
            printWriter.println("No available literature");
        }
    }
}
