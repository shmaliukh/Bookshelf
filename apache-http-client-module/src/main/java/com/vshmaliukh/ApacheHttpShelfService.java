package com.vshmaliukh;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.vshmaliukh.MyApacheUtils.COOKIE_HEADER;

public class ApacheHttpShelfService extends AbstractSqlHandler {

    public static final String HTTP_LOCALHOST_8082 = "http://localhost:8082/";

    public static final String APPLICATION_JSON = "application/json";
    public static final String INDEX_OF_ITEM = "indexOfItem";
    public static final String CLASS_TYPE = "classType";
    public static final String ITEM_CLASS_TYPE = "itemClassType";

    protected int typeOfWork;
    protected List<String> cookieValueList = new ArrayList<>();

    protected Gson gson = new Gson();

    protected CloseableHttpClient client = HttpClientBuilder.create().build();

    protected ApacheHttpShelfService(String userName, int typeOfWork) {
        super(userName);
        this.typeOfWork = typeOfWork;
        logIn(userName, typeOfWork);
    }

    private void logIn(String userName, int typeOfWork) {
        HttpPost httpPost = new HttpPost(HTTP_LOCALHOST_8082 + "log_in");
        HttpClientContext context = HttpClientContext.create();
        try {
            UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
            String userGsonStr = gson.toJson(userDataModelForJson);
            StringEntity stringEntity = new StringEntity(userGsonStr);
            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Accept", APPLICATION_JSON);
            httpPost.addHeader("Content-type", APPLICATION_JSON);
            CloseableHttpResponse response = client.execute(httpPost, context);
            for (Header header : response.getHeaders()) {
                System.out.println(header.getName() + " = " + header.getValue());
            }
            CookieStore cookieStore = context.getCookieStore();
            for (Cookie cookie : cookieStore.getCookies()) {
                cookieValueList.add(MyApacheUtils.generateCookieValue(cookie.getName(), cookie.getValue()));
            }
            MyLogUtil.logDebug(this, "userNamed: '{}', typeOfWork: '{}' // cookies: '{}'", userName, typeOfWork, cookieStore.getCookies());
        } catch (IOException e) {
            MyLogUtil.logErr(this, e);
        }
    }

    @Override
    public void deleteItemFromDB(Item item) {
        HttpGet httpGet = new HttpGet(HTTP_LOCALHOST_8082 + "delete_item");
        cookieValueList.forEach(o -> httpGet.addHeader(COOKIE_HEADER, o));
        try {
            List<Item> itemList =  readItemList();
            int index = itemList.indexOf(item) + 1;
            System.out.println(itemList);
            System.out.println("item to delete: " + item + " // index: " + index);
            URI uri = new URIBuilder(httpGet.getUri())
                    .addParameter(INDEX_OF_ITEM, String.valueOf(index))
                    .build();
            httpGet.setUri(uri);
            client.execute(httpGet);
        } catch (Exception e) {
            MyLogUtil.logErr(this, e);
        }
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        HttpGet httpGet = new HttpGet(HTTP_LOCALHOST_8082 + "change_item_borrowed_state");
        cookieValueList.forEach(o -> httpGet.addHeader(COOKIE_HEADER, o));
        try {
            List<Item> itemList = readItemList();
            int index = itemList.indexOf(item) + 1;
            URI uri = new URIBuilder(httpGet.getUri())
                    .addParameter(INDEX_OF_ITEM, String.valueOf(index))
                    .build();
            httpGet.setUri(uri);
            CloseableHttpResponse httpResponse = client.execute(httpGet);
            System.out.println(httpResponse);
        } catch (Exception e) {
            MyLogUtil.logErr(this, e);
        }
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeStr = classType.getSimpleName();
        HttpGet httpGet = new HttpGet(HTTP_LOCALHOST_8082 + "readItemsByType");
        httpGet.addHeader("Content-Type", APPLICATION_JSON);
        cookieValueList.forEach(o -> httpGet.addHeader(COOKIE_HEADER, o));
        httpGet.addHeader(COOKIE_HEADER, CLASS_TYPE + "=" + classTypeStr);
        return readItemListByClassFromResponse(httpGet, classType);
    }

    private <T extends Item> ArrayList<T> readItemListByClassFromResponse(HttpGet httpGet, Class<T> classType) {
        try (CloseableHttpResponse closeableHttpResponse = client.execute(httpGet)) {
            Type listType = TypeToken.getParameterized(ArrayList.class, classType).getType();
            HttpEntity entity = closeableHttpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return gson.fromJson(result, listType);
        } catch (Exception e) {
            MyLogUtil.logErr(this, e);
        }
        return new ArrayList<>();
    }

    @Override
    public void saveItemToDB(Item item) {
        String itemClassTypeStr = item.getClass().getSimpleName();
        HttpPost httpPost = new HttpPost(HTTP_LOCALHOST_8082 + "add_item");
        cookieValueList.forEach(o -> httpPost.addHeader(COOKIE_HEADER, o));
        httpPost.addHeader(COOKIE_HEADER, MyApacheUtils.generateCookieValue(ITEM_CLASS_TYPE, itemClassTypeStr));
        String userGsonStr = gson.toJson(item);
        StringEntity stringEntity = new StringEntity(userGsonStr);
        httpPost.setEntity(stringEntity);
        try {
            client.execute(httpPost);
        } catch (Exception e) {
            MyLogUtil.logErr(this, e);
        }
    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }

}
