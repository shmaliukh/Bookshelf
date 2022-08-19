package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.services.file_service.sql_handler.MySqlHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public class MySqlShelfHandler extends AbstractSqlShelfHandler {

    protected MySqlHandler mySqlHandler;

    public MySqlShelfHandler(String userName) {
        mySqlHandler = new MySqlHandler(System.getProperty("user.home"), userName);
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType){
        return mySqlHandler.readItemsByClass(classType);
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            getShelf().itemsOfShelf.add(item);
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
        mySqlHandler.saveItemList(getShelf().itemsOfShelf);
    }

    @Override
    public void readShelfItems() {
        getShelf().itemsOfShelf = mySqlHandler.readItemList();
    }
}
