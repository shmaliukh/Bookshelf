package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleSqlShelfHandler extends SqlShelfHandler {

    final Scanner scanner;
    final PrintWriter printWriter;

    public ConsoleSqlShelfHandler(Scanner scanner, PrintWriter printWriter, String userName, int typeOfWorkWithSql) {
        super(userName, typeOfWorkWithSql);
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            getShelf().itemsOfShelf.add(item);
            sqlItemHandler.saveItemToDB(item);
            shelf.itemsOfShelf = sqlItemHandler.readItemList();
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        if (!shelf.itemsOfShelf.isEmpty()) {
            if (index > 0 && index <= shelf.itemsOfShelf.size()) {
                Item item = shelf.itemsOfShelf.get(index - 1);
                sqlItemHandler.deleteItemFromDB(item);
                shelf.itemsOfShelf = sqlItemHandler.readItemList();
                printWriter.println(item + " " + "has deleted from shelf");
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
                sqlItemHandler.changeItemBorrowedStateInDB(buffer);
                shelf.itemsOfShelf = sqlItemHandler.readItemList();

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

