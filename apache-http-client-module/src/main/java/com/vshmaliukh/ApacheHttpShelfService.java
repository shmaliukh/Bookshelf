package com.vshmaliukh;

import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public class ApacheHttpShelfService extends AbstractSqlHandler {

    protected ApacheHttpShelfService(String userName) {
        super(userName);
    }

    @Override
    public List<Item> readItemList() {
        return super.readItemList();
    }

    @Override
    public void deleteItemFromDB(Item item) {
        super.deleteItemFromDB(item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        super.changeItemBorrowedStateInDB(item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return super.readItemsByClass(classType);
    }

    @Override
    public void saveItemToDB(Item item) {
        super.saveItemToDB(item);
    }


    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }
}
