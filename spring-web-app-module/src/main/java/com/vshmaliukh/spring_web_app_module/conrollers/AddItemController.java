package com.vshmaliukh.spring_web_app_module.conrollers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.util.Map;

import static org.vshmaliukh.Constants.*;

@Slf4j
@Controller
public class AddItemController {

    public static final String ADD_ITEM_FORM = "addItemForm";
    static Gson gson = new Gson();

    final SpringBootWebUtil springBootWebUtil;

    public AddItemController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @PutMapping("/put_item_to_db")
    <T extends Item> ResponseEntity<Void> addItem(@CookieValue(name = "userName") String userName,
                                                  @CookieValue(name = "typeOfWork") int typeOfWork,
                                                  @RequestBody T item) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        webShelfHandler.addItem(item);
        webShelfHandler.readShelfItems();
        if (webShelfHandler.getShelf().getItemsOfShelf().contains(item)) {
            return ResponseEntity.ok().build();
        }
        log.warn("userName: '{}' // type of work: '{}' // item '{}' not added to shelf", userName, typeOfWork, item);
        return ResponseEntity.badRequest().build();
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
                log.warn("userName: '{}' // type of work: '{}' // problem to add item with fields '{}' index: " +
                        "item fields are not valid or webShelfHandler == null", userName, typeOfWork, jsonBody);
                log.debug("doGet(userName: '{}', typeOfWork: '{}', itemClassType: '{}', jsonBody: '{}', modelMap: '{}') " +
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
