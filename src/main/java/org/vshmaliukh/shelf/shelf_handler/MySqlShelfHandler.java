package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.MySqlHandler;
import org.vshmaliukh.services.file_service.SqlLiteHandler;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.stream.Collectors;


public class MySqlShelfHandler extends SaveReadShelfHandler {

    User user;
    protected MySqlHandler mySqlHandler;

    public MySqlShelfHandler(String userName) {
        this.user = new User(userName); // TODO
        mySqlHandler = new MySqlHandler(System.getProperty("user.home"), user.getName());
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType){
        return mySqlHandler.readItemsByClass(classType);
    }

    @Override
    public List<Item> readLiteratureInShelf() {
        return shelf.itemsOfShelf.stream() // todo
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return shelf.itemsOfShelf.stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            shelf.itemsOfShelf.add(item);
            mySqlHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        Item item = shelf.itemsOfShelf.remove(index - 1);
        mySqlHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        Item item = literatureList.get(index - 1);
        mySqlHandler.changeItemBorrowedStateInDB(item);
    }

    @Override
    public void saveShelfItems() {
        mySqlHandler.saveItemList(shelf.itemsOfShelf);
    }

    @Override
    public void readShelfItems() {
        shelf.itemsOfShelf = mySqlHandler.readItemList();
    }

    @Override
    public void setUpDataSaver(String userName, int typeOfWorkWithFiles) {
        gsonItemHandler = new SqlLiteHandler(HOME_PROPERTY, userName);
    }

    @Override
    public Shelf getShelf() {
        return shelf;
    }
}
