package org.vshmaliukh.shelf.shelf_handler;

import lombok.NoArgsConstructor;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.MySqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.SqliteHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

@NoArgsConstructor
public class SqlShelfHandler extends SaveReadShelfHandler {

    protected AbstractSqlHandler sqlItemHandler;

    public SqlShelfHandler(String userName, int saveReadServiceType) {
        super(userName, saveReadServiceType);
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
        Item item = sqlItemHandler.readItemList().remove(index - 1);
        sqlItemHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        Item item = sqlItemHandler.readItemList().get(index - 1);
        sqlItemHandler.changeItemBorrowedStateInDB(item);
    }

    @Override
    public void saveShelfItems() {
        sqlItemHandler.saveItemListToDB(getShelf().getItemsOfShelf());
    }

    @Override
    public void readShelfItems() {
        getShelf().setItemsOfShelf(sqlItemHandler.readItemList());
    }

    @Override
    public void setUpDataService(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
            case SaveReadShelfHandler.OLD_MODE_WORK_WITH_SQLITE:
                sqlItemHandler = new SqliteHandler(userName);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
            case SaveReadShelfHandler.OLD_MODE_WORK_WITH_MYSQL:
                sqlItemHandler = new MySqlHandler(userName);
                break;
            default:
                sqlItemHandler = new SqliteHandler(userName);
                break;
        }
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType) {
        return sqlItemHandler.readItemsByClass(classType);
    }
}
