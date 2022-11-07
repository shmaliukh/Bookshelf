package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.*;

@Slf4j
public class RestTemplateClientServiceImp extends AbstractHttpShelfService {

    protected RestTemplate restTemplate;
    protected HttpHeaders headers;

    protected RestTemplateClientServiceImp(String userName, int typeOfWork) {
        super(userName, typeOfWork);
    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        headers.add("Accept", APPLICATION_JSON);
        headers.add("Content-type", APPLICATION_JSON);
        String logInPageUrlStr = HTTP_LOCALHOST_8082 + LOG_IN_PAGE;
        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
        String userGsonStr = gson.toJson(userDataModelForJson);
        HttpEntity<String> entity = new HttpEntity<>(userGsonStr, headers);
        ResponseEntity<String> response = restTemplate.exchange(logInPageUrlStr, HttpMethod.POST, entity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieStrList = responseHeaders.get("Set-Cookie");
        if (cookieStrList != null) {
            cookieStrList.forEach(o -> headers.add("Cookie", o));
        }
        else {
            log.warn("problem to set up Cookie values // Set-Cookie list == null");
        }

    }

    public void init() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();

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
