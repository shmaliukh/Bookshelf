package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.Shelf;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class WebShelfHandler extends Terminal {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(baos, true);

        this.user = new User(userName);
        this.shelf = new Shelf(printWriter); // TODO
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        setUpGsonHandler();
        readShelfItemsFromJson();
    }
}
