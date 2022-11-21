package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.google.gson.reflect.TypeToken;
import com.vshmaliukh.httpclientmodule.http_client_services.BaseShelfHttpClient;
import com.vshmaliukh.httpclientmodule.http_client_services.MyHttpClientUtils;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.lang.reflect.Type;
import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.*;

@Component
@NoArgsConstructor
public class ShelfRestTemplateClientImp implements BaseShelfHttpClient {

    protected RestTemplate restTemplate = new RestTemplate();

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
        HttpHeaders authorizationCookieHeaders = MyHttpClientUtils.generateAuthorizationCookieHeaders(userName, typeOfWork);
        HttpEntity<String> httpCookieEntity = new HttpEntity<>(itemClassType, authorizationCookieHeaders);
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
        HttpHeaders authorizationCookieHeaders = MyHttpClientUtils.generateAuthorizationCookieHeaders(userName, typeOfWork);
        HttpEntity<T> httpEntity = new HttpEntity<>(item, authorizationCookieHeaders);
        return restTemplate.exchange(
                PUT_ITEM_TO_DB_PAGE_URL_STR,
                HttpMethod.PUT,
                httpEntity,
                Void.class);
    }

    @Override
    public ResponseEntity<Void> changeItemBorrowedStateAndGetResponse(String userName, int typeOfWork, int indexOfItem) {
        HttpHeaders authorizationCookieHeaders = MyHttpClientUtils.generateAuthorizationCookieHeaders(userName, typeOfWork);
        return restTemplate.exchange(
                CHANGE_ITEM_BORROWED_STATE_BY_INDEX_URL_STR,
                HttpMethod.POST,
                new HttpEntity<>(indexOfItem, authorizationCookieHeaders),
                Void.class);
    }

    @Override
    public ResponseEntity<Void> deleteItemAndGetResponse(String userName, int typeOfWork, int indexOfItem) {
        HttpHeaders authorizationCookieHeaders = MyHttpClientUtils.generateAuthorizationCookieHeaders(userName, typeOfWork);
        return restTemplate.exchange(
                DELETE_ITEM_BY_INDEX_URL_STR,
                HttpMethod.DELETE,
                new HttpEntity<>(indexOfItem, authorizationCookieHeaders),
                Void.class);
    }

}
