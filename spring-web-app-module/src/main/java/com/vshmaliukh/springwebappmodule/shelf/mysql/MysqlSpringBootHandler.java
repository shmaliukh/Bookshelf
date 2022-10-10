package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlItemServiceImp;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlUserServiceImp;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.vshmaliukh.services.file_service.sql_handler.MySqlHandler;
import org.vshmaliukh.services.file_service.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor
@Service
public class MysqlSpringBootHandler extends MySqlHandler {

    MysqlItemServiceImp mysqlItemServiceImp;
    MysqlUserServiceImp mysqlUserServiceImp;

    public MysqlSpringBootHandler(String userName) {
        super("", userName);
    }

    @Override
    public void setUpSettings() {
        readUserId(user);
        if(user.getId() == null){ // todo
            insertUser(userName);
            readUserId(user);
        }
    }

    @Override
    public void readUserId(UserContainer user) {
        Integer userIdByName = mysqlUserServiceImp.readUserIdByName(userName);
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
        mysqlItemServiceImp.addItemByUserId(item, user.getId());
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

    @Autowired
    public void setMysqlItemServiceImp(@Qualifier("mysqlItemServiceImp") MysqlItemServiceImp mysqlItemServiceImp) {
        this.mysqlItemServiceImp = mysqlItemServiceImp;
    }

    @Autowired
    public void setMysqlUserServiceImp(@Qualifier("mysqlUserServiceImp") MysqlUserServiceImp mysqlUserServiceImp) {
        this.mysqlUserServiceImp = mysqlUserServiceImp;
    }

}
