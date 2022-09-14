package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import com.vshmaliukh.springwebappmodule.SpringAppUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.UrlUtil;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static org.vshmaliukh.Constants.*;

@Controller
public class AddItemController extends HttpServlet {

    static Gson gson = new Gson();

    @PostMapping("/" + Constants.ADD_ITEM_TITLE)
    ModelAndView doPost(@CookieValue String userName,
                        @CookieValue int typeOfWork,
                        @RequestParam Map<String, String> allParams,
                        ModelMap modelMap) {
        String itemClassType = allParams.remove(ITEM_CLASS_TYPE);

        modelMap.addAttribute(USER_NAME, userName);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        Map<String, String> itemFieldValueMap = WebUtils.readMapOfItemFields(allParams);
        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (handlerByName.isValidHTMLFormData(itemFieldValueMap) && webShelfHandler != null) {
            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
            webShelfHandler.addItem(item);

            modelMap.addAttribute(ITEM_GSON_STR, gson.toJson(item));
            modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        }
        return new ModelAndView("redirect:/" + Constants.ADD_MENU_TITLE, modelMap);
    }

    @GetMapping("/" + Constants.ADD_ITEM_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam String itemClassType,
                       @RequestParam String isRandom,
                       ModelMap modelMap) {
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);

        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        if (StringUtils.isNotBlank(itemClassType)) {
            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

            stringBuilder.append("" +
                    "<form action = \"" +
                    UrlUtil.generateBaseURLBuilder(Constants.ADD_ITEM_TITLE, userAtr)
                            .addParameter(ITEM_CLASS_TYPE, itemClassType) + "\" " +
                    "method = \"POST\">\n" +
                    "Create " + itemClassType + "\n" +
                    "       <br>\n");

            if (isRandom.equals("true")) {
                stringBuilder.append(handlerByName.generateHTMLFormBodyToCreateItem(WebUtils.RANDOM));
            } else {
                stringBuilder.append(handlerByName.generateHTMLFormBodyToCreateItem());
            }
        }
        stringBuilder.append(HtmlUtil.formHTMLButton(SpringAppUtils.generateUrlString(Constants.ADD_MENU_TITLE), "Back"));

        String generatedHtmlStr = stringBuilder.toString();
        modelMap.addAttribute("generatedHtmlStr", generatedHtmlStr);
        return new ModelAndView(Constants.ADD_ITEM_TITLE, modelMap);
    }
}
