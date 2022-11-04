package com.vshmaliukh.httpclientmodule.http_client_services;

import org.vshmaliukh.services.save_read_services.SaveReadItems;
import org.vshmaliukh.services.save_read_services.sql_handler.SqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface HttpShelfService extends SaveReadItems, SqlHandler {

    void init();

    @Override
    void deleteItemFromDB(Item item);

    @Override
    void changeItemBorrowedStateInDB(Item item);

    @Override
    <T extends Item> List<T> readItemsByClass(Class<T> classType);

    @Override
    void saveItemToDB(Item item);

    @Override
    void setUpSettings();

    @Override
    void readUserId(UserContainer user);

    void logIn(String userName, int typeOfWork);

}
