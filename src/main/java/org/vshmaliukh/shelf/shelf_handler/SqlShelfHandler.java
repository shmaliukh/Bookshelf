package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
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
            getShelf().itemsOfShelf.add(item);
            sqlItemHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        Item item = shelf.itemsOfShelf.remove(index - 1);
        sqlItemHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        Item item = literatureList.get(index - 1);
        sqlItemHandler.changeItemBorrowedStateInDB(item);
    }

    @Override
    public void saveShelfItems() {
        sqlItemHandler.saveItemList(getShelf().itemsOfShelf);
    }

    @Override
    public void readShelfItems() {
        getShelf().itemsOfShelf = sqlItemHandler.readItemList();
    }

    @Override
    public void setUpDataSaver(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_SQLLITE:
                sqlItemHandler = new SqlLiteHandler(HOME_PROPERTY, userName);
                break;
            case FILE_MODE_WORK_WITH_MYSQL:
                sqlItemHandler = new MySqlHandler(HOME_PROPERTY, userName);
                break;
            default:
                sqlItemHandler = new SqlLiteHandler(HOME_PROPERTY, userName);
                break;
        }
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType){
        return sqlItemHandler.readItemsByClass(classType);
    }
}
