package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import java.util.Collections;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.IS_RANDOM;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

@Controller
public class AddMenuController {

    static Gson gson = new Gson();

    @GetMapping("/" + ADD_MENU_TITLE)
    ModelAndView doGet(@RequestParam String userName,
                       @RequestParam int typeOfWork,
                       @RequestParam(required = false) String itemClassType,
                       @RequestParam(required = false) String itemGsonStr,
                       ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");

        stringBuilder.append(HtmlUtil.generateMenuItemsFormHTML(userAtr, ADD_MENU_TITLE, new GeneratedMenuForAdding()));
        stringBuilder.append(HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE));

        stringBuilder.append(generateMessageAboutAddedItem(itemClassType, itemGsonStr));

        model.addAttribute("generatedHtmlStr", stringBuilder.toString());

        return new ModelAndView(ADD_MENU_TITLE, model);
    }

    private String generateMessageAboutAddedItem(String itemClassType, String itemGsonStr) {
        if (itemGsonStr != null && itemClassType != null) {
            Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
            Item item = gson.fromJson(itemGsonStr, classByName);

            return "Added new item:" +
                    " <br>\n " +
                    " <br>\n " +
                    WebUtils.generateTableOfShelfItems(Collections.singletonList(item), false);
        }
        return "";
    }

//    @GetMapping("/" + ADD_MENU_TITLE)
//    ModelAndView doGet(@RequestParam String userName,
//                       @RequestParam int typeOfWork,
//                       ModelMap model) {
//        model.addAttribute(USER_NAME, userName);
//        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
//        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
//
//        String generatedHtmlStr = GeneratedMenu.MESSAGE_TO_ENTER + " <br>" +
//                HtmlUtil.generateMenuItemsFormHTML(userAtr, ADD_ITEM_TITLE, new GeneratedMenuForAdding()) +
//                HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);
//        //webPageBuilder.addMessageBlock(generateMessageAboutAddedItem(request));
//
//        model.addAttribute("generatedHtmlStr", generatedHtmlStr);
//        return new ModelAndView(ADD_MENU_TITLE, model);
//    }


//    @PostMapping("/" + ADD_MENU_TITLE)
//    ModelAndView doPost(@RequestParam String userName,
//                        @RequestParam int typeOfWork,
//                        @RequestParam int menuItemIndex,
//                        ModelMap model) {
//        model.addAttribute(USER_NAME, userName);
//        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
//        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

//        String typeOfWork = request.getParameter(TYPE_OF_WORK_WITH_FILES);
//        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);

//        if (menuItemIndex != null && typeOfWork != null && !menuItemIndex.equals("") && !typeOfWork.equals("")) {
//            GeneratedMenu generatedMenu = new GeneratedMenuForAdding();
//            int parseInt;
//            try {
//                parseInt = Integer.decode(menuItemIndex);
//            } catch (NumberFormatException e) {
//                parseInt = 0;
//            }
//            if (parseInt > 0 && parseInt <= generatedMenu.generatedMenu.size()) {
//                MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(parseInt - 1);
//                int index = menuItemClassType.getIndex();
//                addItemByType(userAtr, response, menuItemClassType, index);
//            }
//        } else {
//            return new ModelAndView(ADD_MENU_TITLE, model);
////            UrlUtil.redirectTo(ADD_MENU_TITLE, response, userAtr);
//        }
//    }

    @PostMapping("/" + ADD_MENU_TITLE)
    ModelAndView doPost(@RequestParam String userName,
                        @RequestParam int typeOfWork,
                        @RequestParam(defaultValue = "0") int menuItemIndex,
                        ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);

        GeneratedMenu generatedMenu = new GeneratedMenuForAdding();

        if (menuItemIndex > 0 && menuItemIndex <= generatedMenu.generatedMenu.size()) {
            MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(menuItemIndex - 1);
            int index = menuItemClassType.getIndex();

            String classSimpleName = menuItemClassType.getClassType().getSimpleName();
            model.addAttribute(ITEM_CLASS_TYPE, classSimpleName);
            if (index % 2 == 0) { //add random item
                model.addAttribute(IS_RANDOM, "true");
            }
            return new ModelAndView("redirect:/" + ADD_ITEM_TITLE, model);
        }
        return new ModelAndView(ADD_MENU_TITLE, model);
    }
}
