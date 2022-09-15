package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.BootstrapHtmlBuilder;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static org.vshmaliukh.BootstrapHtmlBuilder.split;
import static org.vshmaliukh.Constants.*;

@Controller
public class AddItemController extends HttpServlet {

    static Gson gson = new Gson();

    @PostMapping("/" + Constants.ADD_ITEM_TITLE)
    ModelAndView doPost(@CookieValue String userName,
                        @CookieValue int typeOfWork,
                        @RequestParam Map<String, String> allParams,
                        ModelMap modelMap) {
        String itemClassType = allParams.remove(Constants.ITEM_CLASS_TYPE);

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
            modelMap.addAttribute(Constants.ITEM_CLASS_TYPE, itemClassType);
        }
        return new ModelAndView("redirect:/" + Constants.ADD_MENU_TITLE, modelMap);
    }

    @GetMapping("/" + Constants.ADD_ITEM_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam String itemClassType,
                       @RequestParam String isRandom,
                       ModelMap modelMap) {
        modelMap.addAttribute(Constants.ITEM_CLASS_TYPE, itemClassType);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        modelMap.addAttribute(GENERATED_HTML_STR, getStringBuilder(itemClassType, isRandom, userAtr).toString());
        return new ModelAndView(ADD_ITEM_TITLE, modelMap);
    }

    private static StringBuilder getStringBuilder(String itemClassType, String isRandom, Map<String, String> userAtr) {
        StringBuilder sb = new StringBuilder();
//        if (StringUtils.isNotBlank(itemClassType)) {
//            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
//
//            sb.append(
//                    "<form action = \"" +
//                            UrlUtil.generateBaseURLBuilder(Constants.ADD_ITEM_TITLE, userAtr)
//                                    .addParameter(ITEM_CLASS_TYPE, itemClassType) + "\" " +
//                            "method = \"POST\">\n" +
//                            "Create " + itemClassType + "\n" +
//                            "       <br>\n");
//
//            if (isRandom.equals("true")) {
//                sb.append(handlerByName.generateHTMLFormBodyToCreateItem(WebUtils.RANDOM));
//            } else {
//                sb.append(handlerByName.generateHTMLFormBodyToCreateItem());
//            }
//
//            sb = new StringBuilder().append(cardBlue("Add " + itemClassType, sb.toString()));
//        }
        sb.append(split());
        sb.append(BootstrapHtmlBuilder.buttonWithRef("Back", ADD_MENU_TITLE));
        return sb;
    }

}
