package com.vshmaliukh.httpclientmodule.http_client_services.apache_http_client_service;

import com.vshmaliukh.httpclientmodule.HttpClientAppConfig;
import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.HttpHeaders;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.MyUtils;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.*;

@Slf4j
public class ApacheHttpShelfServiceImp extends AbstractHttpShelfService {

    protected List<String> cookieValueList;
    protected CloseableHttpClient client;

    protected ApacheHttpShelfServiceImp(String userName, int typeOfWork) {
        super(userName, typeOfWork);
        init();
        logIn(userName, typeOfWork);
    }

    public void logIn(String userName, int typeOfWork) {
        HttpPost httpPost = new HttpPost(LOG_IN_PAGE_URL_STR);
        HttpClientContext context = HttpClientContext.create();
        try {
            UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
            String userGsonStr = gson.toJson(userDataModelForJson);
            StringEntity stringEntity = new StringEntity(userGsonStr);
            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Accept", APPLICATION_JSON);
            httpPost.addHeader("Content-type", APPLICATION_JSON);
            client.execute(httpPost, context);
            CookieStore cookieStore = context.getCookieStore();
            for (Cookie cookie : cookieStore.getCookies()) {
                cookieValueList.add(MyUtils.generateCookieValue(cookie.getName(), cookie.getValue()));
            }
            MyLogUtil.logDebug(this, "userNamed: '{}', typeOfWork: '{}' // cookies: '{}'",
                    userName, typeOfWork, cookieStore.getCookies());
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
    }

    @Override
    public void init() {
        cookieValueList = new ArrayList<>();
        client = HttpClientBuilder.create().build();
    }

    @Override
    public void deleteItemFromDB(Item item) {
        HttpGet httpGet = new HttpGet(DELETE_ITEM_BY_INDEX_URL_STR);
        actionWithItemByIndex(item, httpGet);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        HttpGet httpGet = new HttpGet(CHANGE_ITEM_BORROWED_STATE_BY_INDEX_URL_STR);
        actionWithItemByIndex(item, httpGet);
    }

    private void actionWithItemByIndex(Item item, HttpGet httpGet) {
        cookieValueList.forEach(o -> httpGet.addHeader(HttpHeaders.COOKIE, o));
        try {
            URI uri = formUriWithItemIndex(item, httpGet.getUri());
            httpGet.setUri(uri);
            client.execute(httpGet);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeSimpleName = classType.getSimpleName();
        HttpGet httpGet = new HttpGet(READ_ITEMS_BY_TYPE_URL_STR);
        cookieValueList.forEach(o -> httpGet.addHeader(HttpHeaders.COOKIE, o));
        httpGet.addHeader("Accept", APPLICATION_JSON);
        httpGet.addHeader("Content-type", APPLICATION_JSON);
        try {
            URI uri = new URIBuilder(httpGet.getUri())
                    .addParameter(HttpClientAppConfig.ITEM_CLASS_TYPE, classTypeSimpleName)
                    .build();
            httpGet.setUri(uri);
            return readItemListByClassFromResponse(httpGet, classType);
        } catch (URISyntaxException urise) {
            log.error(urise.getMessage(), urise);
        }
        return Collections.emptyList();
    }

    private <T extends Item> ArrayList<T> readItemListByClassFromResponse(HttpGet httpGet, Class<T> classType) {
        try (CloseableHttpResponse closeableHttpResponse = client.execute(httpGet)) {
            HttpEntity entity = closeableHttpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return formItemListByTypeFromJsonStr(classType, result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public void saveItemToDB(Item item) {
        String itemClassTypeStr = item.getClass().getSimpleName();
        HttpPost httpPost = new HttpPost(ADD_ITEM_URI_STR);
        cookieValueList.forEach(o -> httpPost.addHeader(HttpHeaders.COOKIE, o));
        httpPost.addHeader(HttpHeaders.COOKIE, MyUtils.generateCookieValue(HttpClientAppConfig.ITEM_CLASS_TYPE, itemClassTypeStr));
        String userGsonStr = gson.toJson(item);
        StringEntity stringEntity = new StringEntity(userGsonStr);
        httpPost.setEntity(stringEntity);
        try {
            client.execute(httpPost);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
