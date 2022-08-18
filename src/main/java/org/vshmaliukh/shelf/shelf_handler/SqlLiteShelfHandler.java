package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.SqlLiteHandler;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.stream.Collectors;


public class SqlLiteShelfHandler extends SaveReadShelfHandler {

    //String userName;
    User user;
    protected SqlLiteHandler sqlLiteHandler;

    public SqlLiteShelfHandler(String userName) {
        //this.userName = userName;
        this.user = new User(userName); // TODO
        sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), user.getName());
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType){
        return sqlLiteHandler.readItemsByClass(classType);
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
            sqlLiteHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        Item item = shelf.itemsOfShelf.remove(index - 1);
        sqlLiteHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        Item item = literatureList.get(index - 1);
        sqlLiteHandler.changeItemBorrowedStateInDB(item);
    }

    @Override
    public void saveShelfItems() {
        sqlLiteHandler.saveItemList(shelf.itemsOfShelf);
    }

    @Override
    public void readShelfItems() {
        shelf.itemsOfShelf = sqlLiteHandler.readItemList();
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
