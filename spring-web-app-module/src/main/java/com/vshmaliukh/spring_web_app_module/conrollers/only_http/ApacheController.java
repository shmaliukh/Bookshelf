package com.vshmaliukh.spring_web_app_module.conrollers.only_http;

import com.google.gson.Gson;
import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
public class ApacheController {


    public static final String READ_ITEMS = "/readItems";
    public static final String READ_ITEMS_BY_TYPE = "/readItemsByType";
    public static final String ADD_ITEM_VIA_APACHE_HTTP_CLIENT = "/addItemViaApacheHttpClient";
    final SpringBootWebUtil springBootWebUtil;
    final Gson gson = new Gson();

    public ApacheController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping(READ_ITEMS_BY_TYPE)
    void readItemLisByClassTypeAsGsonStr(@CookieValue String userName,
                                         @CookieValue int typeOfWork,
                                         @CookieValue String classType,
                                         HttpServletResponse response) {
        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        List<? extends Item> sortedItemsByClass = shelfHandler.getSortedItemsByClass(ItemHandlerProvider.getClassByName(classType));
        response.setContentType("application/json");

        try (PrintWriter responseWriter = response.getWriter()) {
            String sortedItemsByClassJsonStr = gson.toJson(sortedItemsByClass);
            responseWriter.write(sortedItemsByClassJsonStr);
            response.setStatus(HttpServletResponse.SC_OK);
            responseWriter.flush();
        } catch (IOException ioe) {
            MyLogUtil.logErr(this, ioe);
        }
    }

    @PostMapping(ADD_ITEM_VIA_APACHE_HTTP_CLIENT)
    void addItemViaApacheHttpClient(
            @CookieValue String userName,
            @CookieValue int typeOfWork,
            @CookieValue String itemClassType,
            @RequestBody String jsonBody,
            ModelMap modelMap) {
        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        Map<String, String> itemFieldValueMap = gson.fromJson(jsonBody, Map.class); // todo refactor validation to serve JsonStr, not Map
        if (handlerByName.isValidHTMLFormData(itemFieldValueMap) && webShelfHandler != null) {
            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
            webShelfHandler.addItem(item);
        } else {
            MyLogUtil.logWarn(this, "userName: '{}' // type of work: '{}' // problem to add item with fields '{}' index: " +
                    "item fields are not valid or webShelfHandler == null", userName, typeOfWork, jsonBody);
            MyLogUtil.logDebug(this, "doGet(userName: '{}', typeOfWork: '{}', itemClassType: '{}', jsonBody: '{}', modelMap: '{}') " +
                            "// handlerByName: '{}' // springBootWebUtil: '{}' // webShelfHandler: '{}' // itemFieldValueMap: '{}' ",
                    userName, typeOfWork, itemClassType, jsonBody, modelMap, handlerByName, springBootWebUtil, webShelfHandler, itemFieldValueMap);
        }
        System.out.println("ADD_ITEM_VIA_APACHE_HTTP_CLIENT");
    }


}
