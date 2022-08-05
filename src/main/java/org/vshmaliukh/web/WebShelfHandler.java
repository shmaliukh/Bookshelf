package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.Shelf;

public class WebShelfHandler extends Terminal {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.shelf = new Shelf(null); // TODO
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        setUpGsonHandler();
        readShelfItemsFromJson();
    }
}
