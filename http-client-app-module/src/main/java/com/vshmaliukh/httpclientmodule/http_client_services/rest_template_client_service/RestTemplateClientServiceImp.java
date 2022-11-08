package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import com.vshmaliukh.httpclientmodule.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.*;

@Slf4j
public class RestTemplateClientServiceImp extends AbstractHttpShelfService {

    protected RestTemplate restTemplate;
    protected HttpHeaders baseHeaders;

    protected RestTemplateClientServiceImp(String userName, int typeOfWork) {
        super(userName, typeOfWork);
    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        baseHeaders.add("Accept", APPLICATION_JSON);
        baseHeaders.add("Content-type", APPLICATION_JSON);
        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
        String userGsonStr = gson.toJson(userDataModelForJson);
        HttpEntity<String> entity = new HttpEntity<>(userGsonStr, baseHeaders);
        ResponseEntity<String> response = restTemplate.exchange(LOG_IN_PAGE_URL_STR, HttpMethod.POST, entity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieStrList = responseHeaders.get("Set-Cookie");
        if (cookieStrList != null) {
            baseHeaders = new HttpHeaders();
            cookieStrList.forEach(o -> baseHeaders.add(MyUtils.COOKIE_HEADER, o));
        } else {
            log.warn("problem to set up Cookie values // Set-Cookie list == null");
        }
    }

    public void init() {
        restTemplate = new RestTemplate();
        baseHeaders = new HttpHeaders();
    }

    @Override
    public void deleteItemFromDB(Item item) {
        actionWithItemByIndex(DELETE_ITEM_BY_INDEX_URL_STR, item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        actionWithItemByIndex(CHANGE_ITEM_BORROWED_STATE_BY_INDEX_URL_STR, item);
    }

    private void actionWithItemByIndex(String uriStr, Item item) {
        try {
            URI uriWithItemIndex = formUriWithItemIndex(item, URI.create(uriStr));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAll(baseHeaders);
            HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
            restTemplate.exchange(uriWithItemIndex, HttpMethod.GET, requestEntity, Void.class);
        } catch (URISyntaxException urise) {
            log.error(urise.getMessage(), urise);
        }
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> itemClassType) {
        try {
            String itemClassTypeStr = itemClassType.getSimpleName();
            URI uri = new URIBuilder(READ_ITEMS_BY_TYPE_URL_STR)
                    .addParameter(ITEM_CLASS_TYPE, itemClassTypeStr)
                    .build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAll(baseHeaders);
            httpHeaders.add("Accept", APPLICATION_JSON);
            httpHeaders.add("Content-type", APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class);
            String responseJsonBodyStr = response.getBody();
            return formItemListByTypeFromJsonStr(itemClassType, responseJsonBodyStr);
        } catch (URISyntaxException urise) {
            log.error(urise.getMessage(), urise);
        }
        return Collections.emptyList();
    }

    @Override
    public void saveItemToDB(Item item) {
        String itemClassTypeStr = item.getClass().getSimpleName();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(baseHeaders);
        httpHeaders.add(MyUtils.COOKIE_HEADER, MyUtils.generateCookieValue(ITEM_CLASS_TYPE, itemClassTypeStr)); // todo refactor
        HttpEntity<Item> httpEntity = new HttpEntity<>(item, httpHeaders);
//        ResponseEntity<Void> exchange =
        restTemplate.exchange(
                ADD_ITEM_URI_STR,
                HttpMethod.POST,
                httpEntity,
                Void.class);
//        HttpStatus statusCode = exchange.getStatusCode();
//        System.out.println(statusCode.is2xxSuccessful());
    }

}
