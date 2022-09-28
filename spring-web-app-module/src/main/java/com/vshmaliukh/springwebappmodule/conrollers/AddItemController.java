package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static org.vshmaliukh.Constants.*;

@Controller
public class AddItemController extends HttpServlet {

    public static final String ADD_ITEM_FORM = "addItemForm";

    static Gson gson = new Gson();

    @PostMapping("/" + Constants.ADD_ITEM_TITLE)
    ModelAndView doPost(@CookieValue String userName,
                        @CookieValue int typeOfWork,
                        @CookieValue String itemClassType,
                        @RequestBody String jsonBody,
                        ModelMap modelMap) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        Map<String, String> itemFieldValueMap = gson.fromJson(jsonBody, Map.class); // todo refactor validation to serve JsonStr, not Map
        if (handlerByName.isValidHTMLFormData(itemFieldValueMap) && webShelfHandler != null) {
            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
            webShelfHandler.addItem(item);
        }
        return new ModelAndView("redirect:/" + Constants.ADD_MENU_TITLE, modelMap);
    }

    @GetMapping("/" + Constants.ADD_ITEM_TITLE)
    ModelAndView doGet(@CookieValue(defaultValue = "") String itemClassType,
                       @CookieValue String isRandom,
                       ModelMap modelMap) {
        modelMap.addAttribute(ADD_ITEM_FORM, addingFormConfigStr(itemClassType, isRandom));
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView(ADD_ITEM_TITLE, modelMap);
    }

    String addingFormConfigStr(String itemClassType, String isRandom) {
        if (isRandom.equals("true")) {
            return itemClassType + "Rand";
        }
        return itemClassType;
    }

}
