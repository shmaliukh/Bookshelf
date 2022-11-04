package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.HTTP_LOCALHOST_8082;
import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.LOG_IN_PAGE;

public class RestTemplateClientServiceImp extends AbstractHttpShelfService {

    RestTemplate restTemplate;

    protected RestTemplateClientServiceImp(String userName, int typeOfWork) {
        super(userName, typeOfWork);
    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        String logInPageUrlStr = HTTP_LOCALHOST_8082 + LOG_IN_PAGE;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
        String userGsonStr = gson.toJson(userDataModelForJson);
        HttpEntity<String> request = new HttpEntity<String>(userGsonStr, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(logInPageUrlStr, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(exchange);

    }

    public void init() {
        restTemplate = new RestTemplate();
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

    @Override
    public void saveItemListToDB(List<Item> listToSave) {
        super.saveItemListToDB(listToSave);
    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }

}
