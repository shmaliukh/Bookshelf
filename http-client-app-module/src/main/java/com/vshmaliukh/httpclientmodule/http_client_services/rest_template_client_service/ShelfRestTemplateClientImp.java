package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.google.gson.reflect.TypeToken;
import com.vshmaliukh.httpclientmodule.http_client_services.BaseShelfHttpClient;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.vshmaliukh.MyUtils;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.LOG_IN_PAGE_URL_STR;
import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_URL_STR;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_SAVE_READ_SERVICE;
import static org.vshmaliukh.Constants.USER_NAME;

@Component
@NoArgsConstructor
public class ShelfRestTemplateClientImp implements BaseShelfHttpClient {

    protected RestTemplate restTemplate;

    @PostConstruct
    void postConstructInit() {
        restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<Void> logIn(UserDataModelForJson userModel) {
        HttpEntity<UserDataModelForJson> httpEntity = new HttpEntity<>(userModel);
        return restTemplate.exchange(
                LOG_IN_PAGE_URL_STR,
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }

    @Override
    public ResponseEntity<List<? extends Item>> readItemListByClassType(String userName, int typeOfWork, String itemClassType) {
        final HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add(HttpHeaders.COOKIE, MyUtils.generateCookieValue(USER_NAME, userName));
        cookieHeaders.add(HttpHeaders.COOKIE, MyUtils.generateCookieValue(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, typeOfWork));
        HttpEntity<String> httpCookieEntity = new HttpEntity<>(cookieHeaders);
        Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
        Type listType = TypeToken.getParameterized(List.class, classByName).getType();
        return restTemplate.exchange(
                READ_ITEMS_BY_TYPE_URL_STR,
                HttpMethod.POST,
                httpCookieEntity,
                ParameterizedTypeReference.forType(listType));
    }

    @Override
    public <T extends Item> ResponseEntity<Void> saveItemAndGetResponse(String userName, int typeOfWork, T item) {
        return null;
    }

    @Override
    public ResponseEntity<Void> changeItemBorrowedStateAndGetResponse(String userName, int typeOfWork, int itemIndex) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteItemAndGetResponse(String userName, int typeOfWork, int indexOfItem) {
        return null;
    }
}
