package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.services.file_service.sql_handler.SqlLiteHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.stream.Collectors;


public class SqlLiteShelfHandler extends AbstractSqlShelfHandler {

    protected SqlLiteHandler sqlLiteHandler;

    public SqlLiteShelfHandler(String userName) {
        sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), userName);
    }

    public <T extends Item> List<T> getSortedItemsByClass(Class<T> classType) {
        return sqlLiteHandler.readItemsByClass(classType);
    }

    @Override
    public List<Item> readLiteratureInShelf() {
        return getShelf().itemsOfShelf.stream() // todo
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return getShelf().itemsOfShelf.stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            getShelf().itemsOfShelf.add(item);
            sqlLiteHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteItemByIndex(int index) {
        Item item = getShelf().itemsOfShelf.remove(index - 1);
        sqlLiteHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {
        Item item = literatureList.get(index - 1);
        sqlLiteHandler.changeItemBorrowedStateInDB(item);
    }

    @Override
    public void saveShelfItems() {
        sqlLiteHandler.saveItemList(getShelf().itemsOfShelf);
    }

    @Override
    public void readShelfItems() {
        getShelf().itemsOfShelf = sqlLiteHandler.readItemList();
    }
}
