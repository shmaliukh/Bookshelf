package com.vshmaliukh.springwebappmodule.conrollers;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.WebUtils;

import java.util.Collections;

import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.BASE_PAGE_WITH_PLACEHOLDER;
import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.GENERATED_HTML_STR;
import static org.vshmaliukh.Constants.*;

@Controller
public class AddMenuController {

    static Gson gson = new Gson();

    @GetMapping("/" + Constants.ADD_MENU_TITLE)
    ModelAndView doGet(@RequestParam(defaultValue = "") String itemClassType,
                       @RequestParam(defaultValue = "") String itemGsonStr,
                       ModelMap model) {
        String generated;
        if (StringUtils.isNotBlank(itemClassType) && StringUtils.isNotBlank(itemGsonStr)) {
            generated = generatePageHtmlText() + split() + generateMessageAboutAddedItem(itemClassType, itemGsonStr);
        } else {
            generated = generatePageHtmlText();
        }

        model.addAttribute(GENERATED_HTML_STR, generated);
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, model);
    }

    private static String generatePageHtmlText() {
        StringBuilder sb = new StringBuilder();
        String generatedMenu = generateAddingMenuHtmlStr();
        sb.append(
                divContainer(
                        description("Choose type of item to add") + split() +
                                form(Constants.ADD_MENU_TITLE, "post",
                                        div(generatedMenu),
                                        formSubmitButton()
                                ) + split() +
                                buttonWithRef("Back", MAIN_MENU_TITLE)
                )
        );
        return sb.toString();
    }

    private static String generateAddingMenuHtmlStr() {
        StringBuilder generatedMenuBuilder = new StringBuilder();
        for (MenuItem menuItem : new GeneratedMenuForAdding().generatedMenu) {
            generatedMenuBuilder.append(radioButton(menuItem.getStr(), String.valueOf(menuItem.getIndex()), MENU_ITEM_INDEX, false, String.valueOf(menuItem.getIndex())));
        }
        return generatedMenuBuilder.toString();
    }

    private static String generateMessageAboutAddedItem(String itemClassType, String itemGsonStr) {
        if (StringUtils.isNotBlank(itemGsonStr) && StringUtils.isNotBlank(itemClassType)) {
            Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
            Item item = gson.fromJson(itemGsonStr, classByName);
            return divContainer(
                    description("Added new item") +
                            WebUtils.generateTableOfShelfItems(Collections.singletonList(item), false));
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
