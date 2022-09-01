package org.vshmaliukh.shelf;

import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.util.Random;


public abstract class AbstractUI {

    protected Random random = new Random();

    protected User user;
    protected int typeOfWorkWithFiles;
    protected SaveReadShelfHandler shelfHandler;

    public abstract void configShelfHandler();

    public SaveReadShelfHandler getShelfHandler() {
        return shelfHandler;
    }
}
