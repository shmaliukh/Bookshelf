package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import feign.RequestInterceptor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class FeignClientServiceImp extends AbstractHttpShelfService {

    HttpHeaders cookieHeaders = new HttpHeaders();
    ShelfFeignClientController shelfFeignClientController;

    @Autowired
    public FeignClientServiceImp(ShelfFeignClientController shelfFeignClientController) {
        this.shelfFeignClientController = shelfFeignClientController;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            List<String> cookieHeadersValuesAsList = cookieHeaders.getValuesAsList(HttpHeaders.COOKIE);
            requestTemplate.header(HttpHeaders.COOKIE, cookieHeadersValuesAsList);
        };
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
            log.warn("problem to set up 'log_in' Cookie values // Set-Cookie list == null");
        }
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
        String classTypeSimpleName = classType.getSimpleName();
        ResponseEntity<List<? extends Item>> responseEntity = shelfFeignClientController.readItemLisByClassTypeAsGsonStr(userName, typeOfWork, classTypeSimpleName);
        List<? extends Item> itemListFromResponse = responseEntity.getBody();
        return (List<T>) itemListFromResponse;
    }

    @Override
    public void saveItemToDB(Item item) {
        super.saveItemToDB(item);
    }

}
