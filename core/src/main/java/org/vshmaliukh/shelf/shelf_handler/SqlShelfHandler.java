package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.sql_handler.AbstractSqlItemHandler;
import org.vshmaliukh.services.file_service.sql_handler.MySqlHandler;
import org.vshmaliukh.services.file_service.sql_handler.SqlLiteHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public class SqlShelfHandler extends SaveReadShelfHandler {

    protected AbstractSqlItemHandler sqlItemHandler;

    public SqlShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            getShelf().getItemsOfShelf().add(item);
            sqlItemHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        Item item = shelf.getItemsOfShelf().remove(index - 1);
        sqlItemHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        Item item = literatureList.get(index - 1);
        sqlItemHandler.changeItemBorrowedStateInDB(item);
    }

    @Override
    public void saveShelfItems() {
        sqlItemHandler.saveItemList(getShelf().getItemsOfShelf());
    }

    @Override
    public void readShelfItems() {
        getShelf().setItemsOfShelf(sqlItemHandler.readItemList());
    }

    @Override
    public void setUpDataSaver(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLLITE:
                sqlItemHandler = new SqlLiteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                sqlItemHandler = new MySqlHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
            default:
                sqlItemHandler = new SqlLiteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
        }
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType) {
        return sqlItemHandler.readItemsByClass(classType);
    }
}
