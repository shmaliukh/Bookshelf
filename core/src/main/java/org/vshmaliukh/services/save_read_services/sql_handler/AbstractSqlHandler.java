package org.vshmaliukh.services.save_read_services.sql_handler;

import org.vshmaliukh.shelf.literature_items.Item;

import java.sql.Connection;

public interface AbstractSqlHandler {

    Connection getConnectionToDB();

    void createNewTable(String sql);

    void generateTablesIfNotExists();

    void setUpSettings();

    void saveItemToDB(Item item);

    void logSqlHandler(Exception e);

}
