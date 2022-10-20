package org.vshmaliukh.services.save_read_services.sql_handler;

import org.vshmaliukh.shelf.literature_items.Item;

public interface BaseSqlHandler {

    void setUpSettings();

    void readUserId(UserContainer user);

    void saveItemToDB(Item item);

    void deleteItemFromDB(Item item);

    void changeItemBorrowedStateInDB(Item item);

}
