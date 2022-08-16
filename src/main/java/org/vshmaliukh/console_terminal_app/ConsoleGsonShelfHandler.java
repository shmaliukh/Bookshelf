package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;

import java.io.*;
import java.util.*;

public class ConsoleGsonShelfHandler extends GsonShelfHandler {

    final Scanner scanner;
    final PrintWriter printWriter;

    public ConsoleGsonShelfHandler(Scanner scanner, PrintWriter printWriter, String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!shelf.itemsOfShelf.isEmpty()) {
            if (index > 0 && index <= shelf.itemsOfShelf.size()) {
                printWriter.println(shelf.itemsOfShelf.remove(index - 1) + " " + "has deleted from shelf");
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

