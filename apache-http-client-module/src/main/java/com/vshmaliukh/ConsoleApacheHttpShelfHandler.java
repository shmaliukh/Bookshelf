package com.vshmaliukh;

import org.vshmaliukh.console_terminal_app.ConsoleSqlShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleApacheHttpShelfHandler extends ConsoleSqlShelfHandler {

    public ConsoleApacheHttpShelfHandler(Scanner scanner, PrintWriter printWriter, String userName, int typeOfWork) {
        super(scanner, printWriter, userName, typeOfWork);
    }

    @Override
    public void setUpDataService(String userName, int typeOfWork) {
        this.sqlItemHandler = new ApacheHttpShelfService(userName, 4);
    }

    @Override
    public void addItem(Item item) {
        super.addItem(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        super.changeBorrowedStateOfItem(literatureList, index);
    }

    @Override
    public void saveShelfItems() {

    }

    @Override
    public void readShelfItems() {
        super.readShelfItems();
    }
}
