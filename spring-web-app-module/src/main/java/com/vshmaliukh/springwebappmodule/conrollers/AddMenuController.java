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
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.WebUtils;

import java.util.Collections;
import java.util.Map;

import static org.vshmaliukh.Constants.IS_RANDOM;
import static org.vshmaliukh.Constants.ITEM_CLASS_TYPE;

@Controller
public class AddMenuController {

    static Gson gson = new Gson();

    @GetMapping("/" + Constants.ADD_MENU_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(required = false) String itemClassType,
                       @RequestParam(required = false) String itemGsonStr,
                       ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        model.addAttribute("generatedHtmlStr",
                GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n" +
                        HtmlUtil.generateMenuItemsFormHTML(userAtr, Constants.ADD_MENU_TITLE, new GeneratedMenuForAdding()) +
                        HtmlUtil.formHTMLButton(SpringAppUtils.generateUrlString(Constants.MAIN_MENU_TITLE), Constants.MAIN_MENU_TITLE) +
                        generateMessageAboutAddedItem(itemClassType, itemGsonStr));

        return new ModelAndView(Constants.ADD_MENU_TITLE, model);
    }

    private String generateMessageAboutAddedItem(String itemClassType, String itemGsonStr) {
        if (StringUtils.isNotBlank(itemGsonStr) && StringUtils.isNotBlank(itemClassType)) {
            Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
            Item item = gson.fromJson(itemGsonStr, classByName);
            return "Added new item:" +
                    " <br>\n " +
                    " <br>\n " +
                    WebUtils.generateTableOfShelfItems(Collections.singletonList(item), false);
        }
        return "";
    }

    @PostMapping("/" + Constants.ADD_MENU_TITLE)
    ModelAndView doPost(@RequestParam(defaultValue = "0") int menuItemIndex,
                        ModelMap model) {
        GeneratedMenu generatedMenu = new GeneratedMenuForAdding();

        if (menuItemIndex > 0 && menuItemIndex <= generatedMenu.generatedMenu.size()) {
            MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(menuItemIndex - 1);
            int index = menuItemClassType.getIndex();

            String classSimpleName = menuItemClassType.getClassType().getSimpleName();
            model.addAttribute(ITEM_CLASS_TYPE, classSimpleName);
            String randValue = "false";
            if (index % 2 == 0) { //add random item
                randValue = "true";
            }
            model.addAttribute(IS_RANDOM, randValue);
            return new ModelAndView("redirect:/" + Constants.ADD_ITEM_TITLE, model);
        }
        return new ModelAndView(Constants.ADD_MENU_TITLE, model);
    }
}
