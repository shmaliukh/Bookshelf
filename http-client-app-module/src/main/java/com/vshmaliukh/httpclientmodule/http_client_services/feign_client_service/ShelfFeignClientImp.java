package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.HttpClientAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.*;

@Component
public final class ShelfFeignClientImp implements ShelfFeignClient {

    final ShelfFeignClient shelfFeignClient;

    @Autowired
    public ShelfFeignClientImp(ShelfFeignClient shelfFeignClient) {
        this.shelfFeignClient = shelfFeignClient;
    }

    @Override
    @PostMapping("/" + HttpClientAppConfig.LOG_IN_VIA_USER_MODEL_PAGE)
    public ResponseEntity<Void> logIn(UserDataModelForJson userModel) {
        return shelfFeignClient.logIn(userModel);
    }

    @Override
    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE)
    public ResponseEntity<List<? extends Item>> readItemListByClassType(String userName, int typeOfWork, String itemClassType) {
        return shelfFeignClient.readItemListByClassType(userName, typeOfWork, itemClassType);
    }

    @Override
    @PutMapping("/" + HttpClientAppConfig.PUT_ITEM_TO_DB_PAGE)
    public <T extends Item> ResponseEntity<Void> saveItemAndGetResponse(String userName, int typeOfWork, T item) {
        return shelfFeignClient.saveItemAndGetResponse(userName, typeOfWork, item);
    }

    @PostMapping("/" + CHANGE_ITEM_BORROWED_STATE_PAGE)
    public ResponseEntity<Void> changeItemBorrowedStateAndGetResponse(String userName, int typeOfWork, int indexOfItem) {
        return shelfFeignClient.changeItemBorrowedStateAndGetResponse(userName, typeOfWork, indexOfItem);
    }

    @PostMapping("/" + DELETE_ITEM_PAGE)
    public ResponseEntity<Void> deleteItemAndGetResponse(String userName, int typeOfWork, int indexOfItem) {
        return shelfFeignClient.deleteItemAndGetResponse(userName, typeOfWork, indexOfItem);
    }

}
