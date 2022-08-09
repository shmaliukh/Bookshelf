package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.shelf.AbstractShelf;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerUser;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerOneFileUser;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerPerTypeUser;

import java.util.Random;

public abstract class AbstractShelfHandler {

    public static final String DATE_FORMAT_STR = "dd-MM-yyyy"; // todo create file with config (?)

    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 1;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 2;

    public static final String HOME_PROPERTY = System.getProperty("user.home");

    protected Random random;
    protected ItemGsonHandlerUser itemGsonHandler;

    protected AbstractShelf shelf;
    protected User user;
    protected int typeOfWorkWithFiles;

    protected AbstractShelfHandler() {
    }

    protected AbstractShelfHandler(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;
    }

    public void setUpGsonHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                itemGsonHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                itemGsonHandler = new ItemGsonHandlerPerTypeUser(HOME_PROPERTY, user.getName());
                break;
            default:
                itemGsonHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, user.getName());
                break;
        }
    }

    public void saveShelfItemsToJson() {
        itemGsonHandler.saveItemList(shelf.getAllLiteratureObjects());
    }

    public void readShelfItemsFromJson() {
        itemGsonHandler.readItemList().forEach(shelf::addLiteratureObject);
    }

    public AbstractShelf getShelf() {
        return shelf;
    }
}
