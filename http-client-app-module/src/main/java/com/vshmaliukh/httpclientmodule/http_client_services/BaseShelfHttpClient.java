package com.vshmaliukh.httpclientmodule.http_client_services;

import org.springframework.http.ResponseEntity;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

public interface BaseShelfHttpClient {

    ResponseEntity<Void> logIn(UserDataModelForJson userModel);


    ResponseEntity<List<? extends Item>> readItemListByClassType(String userName,
                                                                 int typeOfWork,
                                                                 String itemClassType);

    <T extends Item> ResponseEntity<Void> saveItemAndGetResponse(String userName,
                                                                 int typeOfWork,
                                                                 T item);

    ResponseEntity<Void> changeItemBorrowedStateAndGetResponse(String userName,
                                                               int typeOfWork,
                                                               int indexOfItem);

    ResponseEntity<Void> deleteItemAndGetResponse(String userName,
                                                  int typeOfWork,
                                                  int indexOfItem);
}
