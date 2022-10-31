package com.vshmaliukh;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApacheHttpShelfService extends AbstractSqlHandler {

    Gson gson = new Gson();
    CloseableHttpClient client = HttpClientBuilder.create().build();

    protected ApacheHttpShelfService(String userName) {
        super(userName);
    }

    @Override
    public List<Item> readItemList() {

        HttpGet httpGet = new HttpGet("http://localhost:8080/add_menu?userName=Vlad1&typeOfWork=1&itemGsonStr=%7B%22name%22%3A%22r4sS_wF6V+5Z5LJcm%22%2C%22pagesNumber%22%3A761%2C%22isBorrowed%22%3Afalse%7D&itemClassType=Newspaper");
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String responseEntityStr = EntityUtils.toString(responseEntity);
            System.out.println("getHeaders: " + Arrays.toString(response.getHeaders()));
            System.out.println("getEntity: " + responseEntity);
            System.out.println("responseEntityStr: " + responseEntityStr);
        } catch (Exception e) {
            MyLogUtil.logErr(this, e);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteItemFromDB(Item item) {

    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return Collections.emptyList();
    }

    @Override
    public void saveItemToDB(Item item) {
        String jsonStr = gson.toJson(item);

    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }
}
