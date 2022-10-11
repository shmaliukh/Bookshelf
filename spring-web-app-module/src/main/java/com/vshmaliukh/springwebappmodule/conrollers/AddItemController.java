package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.shelf.SpringBootWebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + ADD_ITEM_TITLE)
public class AddItemController extends HttpServlet {

    public static final String ADD_ITEM_FORM = "addItemForm";
    static Gson gson = new Gson();

    final SpringBootWebUtil springBootWebUtil;

    public AddItemController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @PostMapping()
    ModelAndView doPost(@CookieValue String userName,
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
        }
        return new ModelAndView("redirect:/" + ADD_MENU_TITLE, modelMap);
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue(defaultValue = "") String itemClassType,
                       @CookieValue String isRandom,
                       ModelMap modelMap) {
        modelMap.addAttribute(ADD_ITEM_FORM, ControllerUtils.addFormIsRandomConfigStr(itemClassType, isRandom));
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView(ADD_ITEM_TITLE, modelMap);
    }

}
