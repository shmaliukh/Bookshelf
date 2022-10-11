package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlSqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlSqlUserServiceImp;
import org.springframework.stereotype.Service;
import org.vshmaliukh.services.file_service.sql_handler.MySqlHandler;
import org.vshmaliukh.services.file_service.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.nio.file.Path;
import java.util.List;

@Service
public class MysqlSpringBootHandler extends MySqlHandler {

    final MysqlSqlItemServiceImp mysqlItemServiceImp;
    final MysqlSqlUserServiceImp mysqlUserServiceImp;

    public MysqlSpringBootHandler(MysqlSqlItemServiceImp mysqlItemServiceImp, MysqlSqlUserServiceImp mysqlUserServiceImp) {
        this.mysqlItemServiceImp = mysqlItemServiceImp;
        this.mysqlUserServiceImp = mysqlUserServiceImp;
    }

    @Override
    public void setUpSettings() {
        this.user = new UserContainer(userName);
        readUserId(user);
        initUserIfNotExist();
    }

    private void initUserIfNotExist() {
        if(user.getId() == null){ // todo
            insertUser(userName);
            readUserId(user);
        }
    }

    @Override
    public void readUserId(UserContainer user) {
        Integer userIdByName = mysqlUserServiceImp.readUserIdByName(user.getName());
        user.setId(userIdByName);
    }

    @Override
    public List<Item> readItemList() {
        return mysqlItemServiceImp.readAllItemListByUserId(user.getId());
    }

    @Override
    public void deleteItemFromDB(Item item) {
        mysqlItemServiceImp.deleteItemByUserId(item, user.getId());
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        mysqlItemServiceImp.changeItemBorrowedStateByUserId(item, user.getId());
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return mysqlItemServiceImp.readItemListByClassAndUserId(classType, user.getId());
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }

    @Override
    public void saveItemToDB(Item item) {
        mysqlItemServiceImp.insertItemByUserId(item, user.getId());
    }

    @Override
    public void insertUser(String userName) {
        mysqlUserServiceImp.insertUser(userName);
    }

    @Override
    public String generateFullFileName() {
        return "";
    } // todo (is necessary to implement ???)

    @Override
    public Path generatePathForFileHandler() {
        return null;
    } // todo (is necessary to implement ???)

}
