package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerOneFileUser;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerPerTypeUser;
import org.vshmaliukh.services.file_service.sqllite.SqlLiteHandler;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class AbstractShelfHandler {

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd"; // todo create file with config (?)

    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 1;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 2;
    public static final int FILE_MODE_WORK_WITH_SQLLITE = 3;

    public static final String HOME_PROPERTY = System.getProperty("user.home");

    protected Random random;
    protected SaveReadUserFilesHandler saveReadUserFilesHandler;

    protected Shelf shelf;
    protected User user;
    protected int typeOfWorkWithFiles;

    protected AbstractShelfHandler() {
        this.shelf = new Shelf();
    }

    public List<Item> readLiteratureInShelf() {
        return getShelf().itemsOfShelf.stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    public List<Item> readLiteratureOutShelf() {
        return getShelf().itemsOfShelf.stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    protected AbstractShelfHandler(String userName, int typeOfWorkWithFiles) {
        this();
        this.user = new User(userName);
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;
    }

    public void addLiteratureObject(Item item) {
        if (item != null) {
            shelf.itemsOfShelf.add(item);
        } else {
            //log.error("The literature item to add is null");
        }
    }

    public abstract void deleteLiteratureObjectByIndex(int index);

    public abstract void changeBorrowedStateOfItem(List<Item> literatureList, int index);

    public void setUpGsonHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                saveReadUserFilesHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                saveReadUserFilesHandler = new ItemGsonHandlerPerTypeUser(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_SQLLITE:
                saveReadUserFilesHandler = new SqlLiteHandler(HOME_PROPERTY, user.getName());
                break;
            default:
                saveReadUserFilesHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, user.getName());
                break;
        }
    }

    public void saveShelfItemsToJson() {
        saveReadUserFilesHandler.saveItemList(shelf.getAllLiteratureObjects());
    }

    public void readShelfItemsFromJson() {
        saveReadUserFilesHandler.readItemList().forEach(this::addLiteratureObject);
    }

    public Shelf getShelf() {
        return shelf;
    }
}
