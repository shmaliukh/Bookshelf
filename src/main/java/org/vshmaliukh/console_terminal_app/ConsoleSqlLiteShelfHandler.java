package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlLiteShelfHandler;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleSqlLiteShelfHandler extends SqlLiteShelfHandler {

    final Scanner scanner;
    final PrintWriter printWriter;

    public ConsoleSqlLiteShelfHandler(Scanner scanner, PrintWriter printWriter, String username) {
        super(username);
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!shelf.itemsOfShelf.isEmpty()) {
            if (index > 0 && index <= shelf.itemsOfShelf.size()) {
                Item item = shelf.itemsOfShelf.remove(index - 1);
                printWriter.println(item + " " + "has deleted from shelf");
                sqlLiteHandler.deleteItemFromDB(item);
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

