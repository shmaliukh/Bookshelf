package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        String logInPageUrlStr = HTTP_LOCALHOST_8082 + LOG_IN_PAGE;
        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
        String userGsonStr = gson.toJson(userDataModelForJson);
        HttpEntity<String> entity = new HttpEntity<>(userGsonStr, baseHeaders);
        ResponseEntity<String> response = restTemplate.exchange(logInPageUrlStr, HttpMethod.POST, entity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieStrList = responseHeaders.get("Set-Cookie");
        if (cookieStrList != null) {
            baseHeaders = new HttpHeaders();
            cookieStrList.forEach(o -> baseHeaders.add("Cookie", o));
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
        super.deleteItemFromDB(item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        super.changeItemBorrowedStateInDB(item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> itemClassType) {
        String urlStr = HTTP_LOCALHOST_8082 + READ_ITEMS_BY_TYPE_PAGE;
        try {
            String classTypeSimpleName = itemClassType.getSimpleName();
            URI uri = new URIBuilder(urlStr)
                    .addParameter("itemClassType", classTypeSimpleName)
                    .build();
            HttpHeaders httpHeaders = baseHeaders;
            httpHeaders.add("Accept", APPLICATION_JSON);
            httpHeaders.add("Content-type", APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(baseHeaders);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            String responseJsonBodyStr = response.getBody();
            return formItemListByTypeFromJsonStr(itemClassType, responseJsonBodyStr);
        } catch (URISyntaxException urise) {
            log.error(urise.getMessage(), urise);
        }
        return Collections.emptyList();
    }

    @Override
    public void saveItemToDB(Item item) {
        super.saveItemToDB(item);
    }

    @Override
    public void saveItemListToDB(List<Item> listToSave) {
        super.saveItemListToDB(listToSave);
    }

}
