package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class FeignClientServiceImp extends AbstractHttpShelfService {

    public static final String SUCCESSFUL_DELETED_ITEM_MESSAGE_STR = "successful deleted '{}' item";
    public static final String PROBLEM_TO_DELETE_ITEM_MESSAGE_STR = "problem to delete '{}' item";
    public static final String SUCCESSFUL_CHANGED_BORROWED_STATE_FOR_ITEM_MESSAGE_STR = "successful changed borrowed state for '{}' item";
    public static final String PROBLEM_TO_CHANGE_BORROWED_STATE_FOR_ITEM_MESSAGE_STR = "problem to change borrowed state for '{}' item";
    public static final String SUCCESSFUL_ADDED_ITEM_MESSAGE_STR = "successful added '{}' item";
    public static final String PROBLEM_TO_ADD_ITEM_TO_DB_MESSAGE_STR = "problem to add '{}' item to db";

    static HttpHeaders cookieHeaders = new HttpHeaders(); // todo fix
    protected ShelfFeignClientImp shelfFeignClientController;

    @Autowired
    public FeignClientServiceImp(ShelfFeignClientImp shelfFeignClientController) {
        this.shelfFeignClientController = shelfFeignClientController;
    }

    @Override
    public void init() {

    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        this.userName = userName;
        this.typeOfWork = typeOfWork;

        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);

        ResponseEntity<Void> responseEntity = shelfFeignClientController.logIn(userDataModelForJson);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        List<String> cookieStrList = responseHeaders.get(HttpHeaders.SET_COOKIE);
        if (cookieStrList != null) {
            cookieHeaders.addAll(HttpHeaders.COOKIE, cookieStrList);
        } else {
            log.warn("problem to set up 'log_in' cookie values // Set-Cookie list == null");
        }
    }

    @Override
    public void deleteItemFromDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfFeignClientController.deleteItemAndGetResponse(userName, typeOfWork, itemIndex);
        informAboutResponseStatus(
                responseEntity,
                SUCCESSFUL_DELETED_ITEM_MESSAGE_STR,
                PROBLEM_TO_DELETE_ITEM_MESSAGE_STR,
                item);
    }



    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfFeignClientController.changeItemBorrowedStateAndGetResponse(userName, typeOfWork, itemIndex);
        informAboutResponseStatus(
                responseEntity,
                SUCCESSFUL_CHANGED_BORROWED_STATE_FOR_ITEM_MESSAGE_STR,
                PROBLEM_TO_CHANGE_BORROWED_STATE_FOR_ITEM_MESSAGE_STR,
                item);
    }


    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeSimpleName = classType.getSimpleName();
        ResponseEntity<List<? extends Item>> responseEntity = shelfFeignClientController.readItemListByClassType(userName, typeOfWork, classTypeSimpleName);
        List<? extends Item> itemListFromResponse = responseEntity.getBody();
        return (List<T>) itemListFromResponse;
    }

    @Override
    public void saveItemToDB(Item item) {
        ResponseEntity<Void> responseEntity = shelfFeignClientController.saveItemAndGetResponse(userName, typeOfWork, item);
        informAboutResponseStatus(
                responseEntity,
                SUCCESSFUL_ADDED_ITEM_MESSAGE_STR,
                PROBLEM_TO_ADD_ITEM_TO_DB_MESSAGE_STR,
                item);
    }

    private void informAboutResponseStatus(ResponseEntity<Void> responseEntity,
                                           String messageIfSuccessStr,
                                           String messageIfProblemStr,
                                           Object... values) {
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("userName: '{}' // typeOfWork: '{}' // " + messageIfSuccessStr,
                    userName, typeOfWork, values);
        } else {
            log.warn("userName: '{}' // typeOfWork: '{}' // " + messageIfProblemStr + " // response status code: '{}'",
                    userName, typeOfWork, values, statusCode);
        }
    }

}
