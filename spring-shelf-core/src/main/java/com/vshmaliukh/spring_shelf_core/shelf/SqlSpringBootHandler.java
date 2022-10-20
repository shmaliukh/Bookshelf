package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlUserService;
import com.vshmaliukh.spring_shelf_core.utils.MyLogUtil;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlItemService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandlerImp;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.sql.Connection;
import java.util.List;

@Slf4j
@NoArgsConstructor
public abstract class SqlSpringBootHandler extends AbstractSqlHandlerImp {

    boolean isNeedLog = true;

    protected SqlItemService itemServiceImp;
    protected SqlUserService userServiceImp;

    @Override
    public void setUpSettings() {
        this.user = new UserContainer(userName);
        readUserId(user);
        initUserIfNotExist();
    }

    private void initUserIfNotExist() {
        if (user.getId() == null) {
            insertUser(userName);
            readUserId(user);
            if (isNeedLog) {
                MyLogUtil.logInfo(userServiceImp, " registered " + user);
            }
        }
    }

    @Override
    public void readUserId(UserContainer user) {
        Integer userIdByName = userServiceImp.readUserIdByName(user.getName());
        user.setId(userIdByName);
    }

    @Override
    public List<Item> readItemList() {
        return itemServiceImp.readAllItemListByUserId(user.getId());
    }

    @Override
    public void deleteItemFromDB(Item item) {
        itemServiceImp.deleteItemByUserId(item, user.getId());
        if (isNeedLog) {
            MyLogUtil.logInfo(itemServiceImp, "deleted item" + item, "user: " + user);
        }
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        itemServiceImp.changeItemBorrowedStateByUserId(item, user.getId());
        if (isNeedLog) {
            MyLogUtil.logInfo(itemServiceImp, "changed item " + item + " borrowed state", "user: " + user);
        }
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return itemServiceImp.readItemListByClassAndUserId(classType, user.getId());
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    @Override
    public void saveItemToDB(Item item) {
        itemServiceImp.insertItemByUserId(item, user.getId());
        MyLogUtil.logInfo(itemServiceImp, "save item " + item + " to database ", "user: " + user);
    }

    @Override
    public void insertUser(String userName) {
        userServiceImp.insertUser(userName);
    }

    //unnecessary methods to implement
    @Override
    public void generateTablesIfNotExists() {
    }

    @Override
    public void logSqlHandler(Exception e) {
    }

    @Override
    public void createUser() {
    }

    @Override
    public Connection getConnectionToDB() {
        return null;
    }

    @Override
    public void createNewTable(String sql) {

    }

}
