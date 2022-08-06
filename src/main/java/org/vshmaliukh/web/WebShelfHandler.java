package org.vshmaliukh.web;

import org.vshmaliukh.console_terminal.ShelfHandler;

public class WebShelfHandler extends ShelfHandler {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
        this.shelf = new WebShelf();

        setUpGsonHandler();
        readShelfItemsFromJson(); // TODO refactor constructor (or delete class)
    }
}
