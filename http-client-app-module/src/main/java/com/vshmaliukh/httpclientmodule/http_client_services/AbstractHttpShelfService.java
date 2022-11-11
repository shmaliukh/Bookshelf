package com.vshmaliukh.httpclientmodule.http_client_services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vshmaliukh.httpclientmodule.HttpClientAppConfig;
import org.apache.hc.core5.net.URIBuilder;
import org.jetbrains.annotations.Nullable;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;
import org.vshmaliukh.shelf.literature_items.Item;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHttpShelfService extends AbstractSqlHandler implements HttpShelfService {

    protected int typeOfWork;
    protected Gson gson = new Gson();

    protected AbstractHttpShelfService(String userName, int typeOfWork) {
        super(userName);
        this.typeOfWork = typeOfWork;
        init();
        logIn(userName, typeOfWork);
    }

    @Nullable
    public <T extends Item> ArrayList<T> formItemListByTypeFromJsonStr(Class<T> classType, String responseJsonStr) {
        Type listType = TypeToken.getParameterized(ArrayList.class, classType).getType();
        return gson.fromJson(responseJsonStr, listType);
    }

    public URI formUriWithItemIndex(Item item, URI uri) throws URISyntaxException {
        List<Item> itemList = readItemList();
        int index = itemList.indexOf(item) + 1;
        return new URIBuilder(uri)
                .addParameter(HttpClientAppConfig.INDEX_OF_ITEM, String.valueOf(index))
                .build();
    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }

}
