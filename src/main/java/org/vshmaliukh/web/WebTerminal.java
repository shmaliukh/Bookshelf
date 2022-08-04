package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.Shelf;

import java.io.PrintWriter;

public class WebTerminal extends Terminal {

    public WebTerminal(String userName, int typeOfWorkWithFiles, PrintWriter printWriter) {
        this.user = new User(userName);
        this.printWriter = printWriter;
        this.shelf = new Shelf(printWriter);
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;
    }

    @Override
    public void printCurrentStateOfShelf() {

    }
}
