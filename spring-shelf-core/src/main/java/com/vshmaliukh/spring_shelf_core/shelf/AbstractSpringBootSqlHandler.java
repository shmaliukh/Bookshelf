package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlItemService;
import com.vshmaliukh.spring_shelf_core.shelf.repository_services.SqlUserService;
import lombok.NoArgsConstructor;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

@NoArgsConstructor
public abstract class AbstractSpringBootSqlHandler extends AbstractSqlHandler {

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
            MyLogUtil.logDebug(this, "userName: '{}' // initUserIfNotExist() // user not exists in db " +
                    "// registered user: '{}'", userName, userContainer);
        }
    }

    @Override
    public void readUserId(UserContainer user) {
        Integer userIdByName = userServiceImp.readUserIdByName(user.getName());
        user.setId(userIdByName);
        MyLogUtil.logDebug(this, "readUserId(user: '{}') // userIdByName: '{}'", user, userIdByName);
    }

    @Override
    public void deleteItemFromDB(Item item) {
        itemServiceImp.deleteItemByUserId(item, userContainer.getId());
        MyLogUtil.logDebug(this, "user: '{}' // deleteItemFromDB(item: '{}')", userContainer, item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        itemServiceImp.changeItemBorrowedStateByUserId(item, userContainer.getId());
        MyLogUtil.logDebug(this, "user: '{}' // changeItemBorrowedStateInDB(item: '{}')", userContainer, item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        List<T> itemListByClassAndUserId = itemServiceImp.readItemListByClassAndUserId(classType, userContainer.getId());
        MyLogUtil.logDebug(this, "user: '{}' // readItemsByClass(item: '{}') // item list from db: '{}'",
                userContainer, classType, itemListByClassAndUserId);
        return itemListByClassAndUserId;
    }

    @Override
    public void saveItemListToDB(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    @Override
    public void saveItemToDB(Item item) {
        itemServiceImp.insertItemByUserId(item, userContainer.getId());
        MyLogUtil.logDebug(this, "user: '{}' // saveItemToDB(item: '{}')", userContainer, item);
    }

}
