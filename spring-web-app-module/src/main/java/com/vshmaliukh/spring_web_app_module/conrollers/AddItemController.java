package com.vshmaliukh.spring_web_app_module.conrollers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.util.Map;

import static org.vshmaliukh.Constants.*;

@Controller
public class AddItemController {

    public static final String ADD_ITEM_FORM = "addItemForm";
    static Gson gson = new Gson();

    final SpringBootWebUtil springBootWebUtil;

    public AddItemController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @PutMapping("/ping/" + ADD_ITEM_TITLE)
    <T extends Item> ResponseEntity<Void> addItem(@CookieValue(name = "userName") String userName,
                                                  @CookieValue(name = "typeOfWork") int typeOfWork,
                                                  @RequestBody T item) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        webShelfHandler.addItem(item);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/" + ADD_ITEM_TITLE)
    ModelAndView doPost(@CookieValue String userName,
                        @CookieValue int typeOfWork,
                        @CookieValue String itemClassType,
                        @RequestBody String jsonBody,
                        ModelMap modelMap) {
        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        try {
            Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
            Item item = gson.fromJson(jsonBody, classByName);
            webShelfHandler.addItem(item);
        } catch (JsonSyntaxException jse) { // todo refactor
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
        }
        //TODO set status according to added item state
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ModelAndView("redirect:/" + ADD_MENU_TITLE, modelMap);
    }

    @GetMapping("/" + ADD_ITEM_TITLE)
    ModelAndView doGet(@CookieValue(defaultValue = "") String itemClassType,
                       @CookieValue String isRandom,
                       ModelMap modelMap) {
        modelMap.addAttribute(ADD_ITEM_FORM, ControllerUtils.addFormIsRandomConfigStr(itemClassType, isRandom));
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView(ADD_ITEM_TITLE, modelMap);
    }

}
