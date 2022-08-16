package org.vshmaliukh.shelf.shelf_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerOneFileUser;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerPerTypeUser;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GsonShelfHandler extends SaveReadShelfHandler {

    protected GsonShelfHandler(){}

    public GsonShelfHandler(String userName, int typeOfWorkWithFiles){
        setUpDataSaver(userName,typeOfWorkWithFiles);
    }

    @Override
    public void setUpDataSaver(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {// todo
            case FILE_MODE_WORK_WITH_ONE_FILE:
                saveReadUserFilesHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, userName);
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                saveReadUserFilesHandler = new ItemGsonHandlerPerTypeUser(HOME_PROPERTY, userName);
                break;
            default:
                saveReadUserFilesHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, userName);
                break;
        }
    }

    @Override
    public List<Item> readLiteratureInShelf() {
        return saveReadUserFilesHandler.readItemList().stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return saveReadUserFilesHandler.readItemList().stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void addLiteratureObject(Item item) {
        if (item != null) {
            shelf.itemsOfShelf.add(item);
        } else {
            log.error("The literature item to add is null");
        }
        saveShelfItems();
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if (!shelf.itemsOfShelf.isEmpty()) {
            if (index > 0 && index <= shelf.itemsOfShelf.size()) {
                shelf.itemsOfShelf.remove(index - 1);
            }
            saveShelfItems();
        }
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        if (!literatureList.isEmpty()) {
            if (index > 0 && index <= literatureList.size()) {
                Item buffer = literatureList.get(index - 1);
                buffer.setBorrowed(!buffer.isBorrowed());
            }
            saveShelfItems();
        }
    }

    @Override
    public void saveShelfItems() {
        saveReadUserFilesHandler.saveItemList(shelf.getAllLiteratureObjects());
    }

    @Override
    public void readShelfItems() {
        saveReadUserFilesHandler.readItemList().forEach(this::addLiteratureObject);
    }

    @Override
    public Shelf getShelf() {
        return shelf;
    }
}
