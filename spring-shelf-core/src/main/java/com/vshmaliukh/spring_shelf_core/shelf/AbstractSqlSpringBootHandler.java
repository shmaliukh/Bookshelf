package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlItemService;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlUserService;
import com.vshmaliukh.spring_shelf_core.utils.MyLogUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

@Slf4j
@NoArgsConstructor
public abstract class AbstractSqlSpringBootHandler extends AbstractSqlHandler {

    boolean isNeedLog = true;

    protected SqlItemService itemServiceImp;
    protected SqlUserService userServiceImp;

    @Override
    public void setUpSettings() {
        this.userContainer = new UserContainer(userName);
        readUserId(userContainer);
        initUserIfNotExist();
    }

    private void initUserIfNotExist() {
        if (userContainer.getId() == null) {
            userServiceImp.insertUser(userName);
            readUserId(userContainer);
            if (isNeedLog) {
                MyLogUtil.logInfo(userServiceImp, " registered " + userContainer);
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
        return itemServiceImp.readAllItemListByUserId(userContainer.getId());
    }

    @Override
    public void deleteItemFromDB(Item item) {
        itemServiceImp.deleteItemByUserId(item, userContainer.getId());
        if (isNeedLog) {
            MyLogUtil.logInfo(itemServiceImp, "deleted item" + item, "user: " + userContainer);
        }
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        itemServiceImp.changeItemBorrowedStateByUserId(item, userContainer.getId());
        if (isNeedLog) {
            MyLogUtil.logInfo(itemServiceImp, "changed item " + item + " borrowed state", "user: " + userContainer);
        }
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return itemServiceImp.readItemListByClassAndUserId(classType, userContainer.getId());
    }

    @Override
    public void saveItemListToDB(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    @Override
    public void saveItemToDB(Item item) {
        itemServiceImp.insertItemByUserId(item, userContainer.getId());
        MyLogUtil.logInfo(itemServiceImp, "save item " + item + " to database ", "user: " + userContainer);
    }

}
