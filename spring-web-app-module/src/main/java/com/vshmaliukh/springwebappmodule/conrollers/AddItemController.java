package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.ADD_ITEM_TITLE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.ADD_MENU_TITLE;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_GSON_STR;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

@Controller
public class AddItemController extends HttpServlet {

    static Gson gson = new Gson();

    @PostMapping("/" + ADD_ITEM_TITLE)
    ModelAndView doPost(@RequestParam Map<String, String> allParams,
//                        @RequestParam String userName,
//                        @RequestParam int typeOfWork,
//                        @RequestParam(required = false) String itemClassType,
                        ModelMap modelMap) {
        String userName = allParams.remove(USER_NAME);
        String typeOfWork = allParams.remove(TYPE_OF_WORK_WITH_FILES);
        String itemClassType = allParams.remove(ITEM_CLASS_TYPE);

        modelMap.addAttribute(USER_NAME, userName);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, Integer.parseInt(typeOfWork));

        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        Map<String, String> itemFieldValueMap = WebUtils.readMapOfItemFields(allParams);
        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (handlerByName.isValidHTMLFormData(itemFieldValueMap) && webShelfHandler != null) {
            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
            webShelfHandler.addItem(item);

            modelMap.addAttribute(ITEM_GSON_STR, gson.toJson(item));
            modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        }
        return new ModelAndView("redirect:/" + ADD_MENU_TITLE, modelMap);

//        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
//
//        Map<String, String> itemFieldValueMap = WebUtils.readMapOfItemFields(modelMap.asMap());
//
//        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
//        if (handlerByName.isValidHTMLFormData(itemFieldValueMap) && webShelfHandler != null) {
//            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
//            webShelfHandler.addItem(item);
//
//            modelMap.addAttribute(ITEM_GSON_STR, gson.toJson(item));
//            modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
//        }
//        return new ModelAndView("redirect:/" + ADD_MENU_TITLE, new ModelMap().addAllAttributes(modelMap.asMap()));
        //return new ModelAndView("add_item", modelMap);
    }

    @GetMapping("/" + ADD_ITEM_TITLE)
    ModelAndView doGet(@RequestParam String userName,
                       @RequestParam int typeOfWork,
                       @RequestParam String itemClassType,
                       @RequestParam String isRandom,
                       ModelMap modelMap) {
        modelMap.addAttribute(USER_NAME, userName);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);


        StringBuilder stringBuilder = new StringBuilder();
//        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);
//        String isRandom = request.getParameter(IS_RANDOM);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        if (itemClassType != null && !itemClassType.equals("")) {
            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

            stringBuilder.append("" +
                    "<form action = \"" +
                    UrlUtil.generateBaseURLBuilder(ADD_ITEM_TITLE, userAtr)
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
        stringBuilder.append(HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLString(ADD_MENU_TITLE, userAtr), "Back"));

        String generatedHtmlStr = stringBuilder.toString();
        modelMap.addAttribute("generatedHtmlStr", generatedHtmlStr);
        return new ModelAndView(ADD_ITEM_TITLE, modelMap);
    }
}
