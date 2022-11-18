package com.vshmaliukh.httpclientmodule.http_client_services;

import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.http_client_services.MyHttpClientUtils.informAboutResponseStatus;

@NoArgsConstructor
public abstract class AbstractSpringClientService extends AbstractHttpShelfService {

    protected BaseShelfHttpClient shelfClientImp;

    @Override
    public void init() {}

    protected void init(String userName, int typeOfWork) {
        this.userName = userName;
        this.typeOfWork = typeOfWork;
    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        init(userName, typeOfWork);

        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
        ResponseEntity<Void> responseEntity = shelfClientImp.logIn(userDataModelForJson);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_LOG_IN_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_LOG_IN_MESSAGE_STR,
                userName, typeOfWork);
    }

    @Override
    public void deleteItemFromDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfClientImp.deleteItemAndGetResponse(userName, typeOfWork, itemIndex);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_DELETED_ITEM_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_DELETE_ITEM_MESSAGE_STR,
                userName, typeOfWork, item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfClientImp.changeItemBorrowedStateAndGetResponse(userName, typeOfWork, itemIndex);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_CHANGED_BORROWED_STATE_FOR_ITEM_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_CHANGE_BORROWED_STATE_FOR_ITEM_MESSAGE_STR,
                userName, typeOfWork, item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeSimpleName = classType.getSimpleName();
        ResponseEntity<List<? extends Item>> responseEntity = shelfClientImp.readItemListByClassType(userName, typeOfWork, classTypeSimpleName);
        List<? extends Item> itemListFromResponse = responseEntity.getBody();
        return (List<T>) itemListFromResponse;
    }

    @Override
    public void saveItemToDB(Item item) {
        ResponseEntity<Void> responseEntity = shelfClientImp.saveItemAndGetResponse(userName, typeOfWork, item);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_ADDED_ITEM_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_ADD_ITEM_TO_DB_MESSAGE_STR,
                userName, typeOfWork, item);
    }
}
