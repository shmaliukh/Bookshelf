package com.vshmaliukh.httpclientmodule.apache_http_client;

import org.vshmaliukh.services.save_read_services.SaveReadItems;
import org.vshmaliukh.services.save_read_services.sql_handler.SqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface ApacheHttpShelfService extends SaveReadItems, SqlHandler {

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

}
