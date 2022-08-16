package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.services.file_service.sqllite.SqlLiteHandler;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.ShelfHandlerInterface;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.util.List;
import java.util.stream.Collectors;

public class SqlLiteShelfHandler implements ShelfHandlerInterface {

    String userName;
    Shelf shelf = new Shelf();

    SqlLiteHandler sqlLiteHandler;

    public SqlLiteShelfHandler(String userName, int typeOfWork) {
        this.userName = userName;

        User user = new User(userName);
        sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), user);
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
    public void addLiteratureObject(Item item) {
        if (item != null) {
            shelf.itemsOfShelf.add(item);
            sqlLiteHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        Item item = shelf.itemsOfShelf.remove(index - 1);
        sqlLiteHandler.deleteItemFromDB(item);
    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {

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
    public Shelf getShelf() {
        return shelf;
    }
}
