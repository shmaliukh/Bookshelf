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
    User user = new User(userName);
    Shelf shelf = new Shelf();
    SqlLiteHandler sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), user);

    @Override
    public List<Item> readLiteratureInShelf() {
        return sqlLiteHandler.readItemList().stream()
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return sqlLiteHandler.readItemList().stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void addLiteratureObject(Item item) {
        if(item != null){
            sqlLiteHandler.saveItemToDB(item);
        }
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {

    }

    @Override
    public void changeBorrowedStateOfItem(List<Item> literatureList, int index) {

    }

    @Override
    public void saveShelfItems() {

    }

    @Override
    public void readShelfItems() {

    }

    @Override
    public Shelf getShelf() {
        return null;
    }
}
