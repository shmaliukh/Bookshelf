package org.vshmaliukh.shelf.shelf_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.save_read_services.gson_handler.ItemGsonHandlerHandler;
import org.vshmaliukh.services.save_read_services.gson_handler.ItemGsonHandlerOneFileForUser;
import org.vshmaliukh.services.save_read_services.gson_handler.ItemGsonHandlerPerTypeForUser;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GsonShelfHandler extends SaveReadShelfHandler {

    protected ItemGsonHandlerHandler itemHandler;

    public GsonShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
    }

    @Override
    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType){
        return itemHandler.readItemsByClass(classType);
    }

    @Override
    public void setUpDataService(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
                itemHandler = new ItemGsonHandlerOneFileForUser(userName);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                itemHandler = new ItemGsonHandlerPerTypeForUser(userName);
                break;
            default:
                itemHandler = new ItemGsonHandlerOneFileForUser(userName);
                break;
        }
    }

    @Override
    public List<Item> readLiteratureInShelf() {
        return itemHandler.readItemList().stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return itemHandler.readItemList().stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            shelf.getItemsOfShelf().add(item);
        } else {
            log.error("The literature item to add is null");
        }
        saveShelfItems();
    }

    @Override
    public void deleteItemByIndex(int index) {
        if (!shelf.getItemsOfShelf().isEmpty()) {
            if (index > 0 && index <= shelf.getItemsOfShelf().size()) {
                shelf.getItemsOfShelf().remove(index - 1);
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
        itemHandler.saveItemList(shelf.getAllLiteratureObjects());
    }

    @Override
    public void readShelfItems() {
        shelf.setItemsOfShelf(new ArrayList<>());
        itemHandler.readItemList().forEach(this::addItem);
    }

}
