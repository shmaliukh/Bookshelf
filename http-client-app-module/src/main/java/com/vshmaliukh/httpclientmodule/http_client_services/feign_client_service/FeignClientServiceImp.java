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

    static HttpHeaders cookieHeaders = new HttpHeaders(); // todo fix
    ShelfFeignClientImp shelfFeignClientController;

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

        ResponseEntity responseEntity = shelfFeignClientController.logIn(userDataModelForJson);
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
        super.deleteItemFromDB(item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfFeignClientController.changeBorrowedStateAndGetResponse(userName, typeOfWork, itemIndex);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("userName: '{}' // typeOfWork: '{}' // successful changed borrowed state for '{}' item",
                    userName, typeOfWork, item);
        } else {
            log.warn("userName: '{}' // typeOfWork: '{}' // problem change borrowed state for '{}' item // response status code: '{}'",
                    userName, typeOfWork, item, statusCode);
        }
    }


    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeSimpleName = classType.getSimpleName();
        ResponseEntity<List<? extends Item>> responseEntity = shelfFeignClientController.readItemListByClassType(userName, typeOfWork, classTypeSimpleName);
        List<? extends Item> itemListFromResponse = responseEntity.getBody();
        return (List<T>) itemListFromResponse; // FIXME
    }

    @Override
    public void saveItemToDB(Item item) {
        ResponseEntity<Void> responseEntity = shelfFeignClientController.saveItem(userName, typeOfWork, item);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("userName: '{}' // typeOfWork: '{}' // successful added '{}' item",
                    userName, typeOfWork, item);
        } else {
            log.warn("userName: '{}' // typeOfWork: '{}' // problem to add '{}' item to db // response status code: '{}'",
                    userName, typeOfWork, item, statusCode);
        }
    }

}
