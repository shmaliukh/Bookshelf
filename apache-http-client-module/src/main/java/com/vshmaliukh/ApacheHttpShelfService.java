package com.vshmaliukh;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApacheHttpShelfService extends AbstractSqlHandler {

    public static final String USER_NAME = "userName";
    public static final String TYPE_OF_WORK = "typeOfWork";
    public static final String ITEM_CLASS_TYPE = "itemClassType";
    public static final String APPLICATION_JSON = "application/json";

    protected int typeOfWork;
    protected Gson gson = new Gson();
    protected CloseableHttpClient client = HttpClientBuilder.create().build();

    protected ApacheHttpShelfService(String userName, int typeOfWork) {
        super(userName);
        this.typeOfWork = typeOfWork;
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

    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        HttpGet httpGet = new HttpGet("http://localhost:8082/readItemsByType");
        httpGet.setHeader("Content-Type", APPLICATION_JSON);
        String classTypeStr = classType.getSimpleName();
        try {
            URI uri = new URIBuilder(httpGet.getUri())
                    .addParameter(USER_NAME, userName)
                    .addParameter(TYPE_OF_WORK, String.valueOf(typeOfWork))
                    .addParameter("classType", classTypeStr)
                    .build();
            httpGet.setUri(uri);
        } catch (URISyntaxException urise) {
            MyLogUtil.logErr(this, urise);
        }
        return readItemListByClassType(httpGet);
    }

    private <T extends Item> ArrayList<T> readItemListByClassType(HttpGet httpGet) {
        try (CloseableHttpResponse closeableHttpResponse = client.execute(httpGet)) {
            Type listType = new TypeToken<ArrayList<T>>() {
            }.getType();
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
        HttpPost post = new HttpPost("http://localhost:8082/addItemViaApacheHttpClient");
        post.setHeader("Cookie", USER_NAME + "=" + userName);
        post.setHeader("Cookie", TYPE_OF_WORK + "=" + typeOfWork);
        post.setHeader("Cookie", ITEM_CLASS_TYPE + "=" + item.getClass().getSimpleName());
        post.setHeader("Content-Type", APPLICATION_JSON);
        StringEntity postingString = new StringEntity(gson.toJson(item));
        post.setEntity(postingString);
        try {
            client.execute(post);
            MyLogUtil.logInfo(this, USER_NAME + ": '{}', " + TYPE_OF_WORK + ": '{}' // added item: '{}'", userName, typeOfWork, item);
        } catch (IOException ioe) {
            MyLogUtil.logWarn(this, USER_NAME + ": '{}', " + TYPE_OF_WORK + ": '{}' // problem to add item: '{}'", userName, typeOfWork, item);
            MyLogUtil.logErr(this, ioe);
        }
    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }

}
