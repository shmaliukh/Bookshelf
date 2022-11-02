package com.vshmaliukh;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApacheHttpShelfService extends AbstractSqlHandler {

    public static final String USER_NAME = "userName";
    public static final String TYPE_OF_WORK = "typeOfWork";
    public static final String ITEM_CLASS_TYPE = "itemClassType";
    public static final String APPLICATION_JSON = "application/json";
    public static final String INDEX_OF_ITEM = "indexOfItem";
    public static final String CLASS_TYPE = "classType";

    protected int typeOfWork;
    protected Gson gson = new Gson();
    protected CookieStore cookieStore = new BasicCookieStore();
    protected CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultCookieStore(cookieStore)
            .build();

    protected ApacheHttpShelfService(String userName, int typeOfWork) {
        super(userName);
        this.typeOfWork = typeOfWork;
        logIn(userName, typeOfWork);
    }

    private void logIn(String userName, int typeOfWork) {
        HttpPost post = new HttpPost("http://localhost:8082/log_in");
        HttpClientContext context = HttpClientContext.create();
        try {
            UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
            String userGsonStr = gson.toJson(userDataModelForJson);
            StringEntity stringEntity = new StringEntity(userGsonStr);
            post.setEntity(stringEntity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(post, context);
            for (Header header : response.getHeaders()) {
                System.out.println(header.getName() + " = " + header.getValue());
            }
            cookieStore = context.getCookieStore();
            System.out.println(cookieStore.getCookies());
            MyLogUtil.logInfo(this, "user: '{}', typeOfWork: '{}' // cookies: '{}'", userName, typeOfWork, cookieStore.getCookies());
        } catch (IOException e) {
            MyLogUtil.logErr(this, e);
        }
    }

    @Override
    public List<Item> readItemList() {
        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> uniqueTypeName : ItemHandlerProvider.uniqueTypeNames) {
            itemList.addAll(readItemsByClass(uniqueTypeName));
        }
        return itemList;
    }

    @Override
    public void deleteItemFromDB(Item item) {

    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        HttpGet httpGet = new HttpGet("http://localhost:8082/change_item_borrowed_state");
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeStr = classType.getSimpleName();
        HttpGet httpGet = new HttpGet("http://localhost:8082/readItemsByType");
        httpGet.addHeader("Content-Type", APPLICATION_JSON);
        httpGet.addHeader("Cookie", USER_NAME+"="+userName);
        httpGet.addHeader("Cookie", TYPE_OF_WORK+"="+typeOfWork);
        httpGet.addHeader("Cookie", ITEM_CLASS_TYPE+"="+classTypeStr);
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

    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }

}
