package org.vshmaliukh.console_terminal;

import org.vshmaliukh.bookshelf.AbstractShelf;
import org.vshmaliukh.console_terminal.services.file_service.ItemGsonHandler;
import org.vshmaliukh.console_terminal.services.file_service.ItemGsonHandlerOneFile;
import org.vshmaliukh.console_terminal.services.file_service.ItemGsonHandlerPerType;

import java.util.Random;

public abstract class ShelfHandler {

    public static final String DATE_FORMAT_STR = "dd-MM-yyyy"; // todo create file with config (?)

    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 1;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 2;

    public static final String HOME_PROPERTY = System.getProperty("user.home");

    protected Random random;
    protected ItemGsonHandler itemGsonHandler;

    protected AbstractShelf shelf;
    protected User user;
    protected int typeOfWorkWithFiles;

    protected ShelfHandler() {
    }

    protected ShelfHandler(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;
    }

    public void setUpGsonHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                itemGsonHandler = new ItemGsonHandlerOneFile(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                itemGsonHandler = new ItemGsonHandlerPerType(HOME_PROPERTY, user.getName());
                break;
            default:
                itemGsonHandler = new ItemGsonHandlerOneFile(HOME_PROPERTY, user.getName());
                break;
        }
    }

    public void saveShelfItemsToJson() {
        itemGsonHandler.saveItemListToFile(shelf.getAllLiteratureObjects());
    }

    public void readShelfItemsFromJson() {
        itemGsonHandler.readItemListFromFile().forEach(shelf::addLiteratureObject);
    }

    public AbstractShelf getShelf() {
        return shelf;
    }
}
