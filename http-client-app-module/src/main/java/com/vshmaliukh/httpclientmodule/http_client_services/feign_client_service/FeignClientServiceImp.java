package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.Collections;
import java.util.List;

public class FeignClientServiceImp extends AbstractHttpShelfService {

    protected FeignClientServiceImp(String userName, int typeOfWork) {
        super(userName, typeOfWork);
    }

    @Override
    public void init() {

    }

    @Override
    public void logIn(String userName, int typeOfWork) {

    }

    @Override
    public List<Item> readItemList() {
        return Collections.emptyList();
    }

    @Override
    public void deleteItemFromDB(Item item) {
        super.deleteItemFromDB(item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        super.changeItemBorrowedStateInDB(item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return super.readItemsByClass(classType);
    }

    @Override
    public void saveItemToDB(Item item) {
        super.saveItemToDB(item);
    }
}
