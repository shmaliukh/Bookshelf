package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;

import javax.servlet.http.HttpServletResponse;

import static org.vshmaliukh.Constants.*;

@Controller
public class AddMenuController {

    public static final String GENERATED_MENU = "generatedMenu";

    @GetMapping("/" + Constants.ADD_MENU_TITLE)
    ModelAndView doGet(ModelMap model) {
        model.addAttribute(GENERATED_MENU, ControllerUtils.generateRadioButtonsMenuHtmlStr(new GeneratedMenuForAdding().generatedMenu));
        return new ModelAndView(ADD_MENU_TITLE, model);
    }

    @PostMapping("/" + Constants.ADD_MENU_TITLE)
    ModelAndView doPost(@RequestParam(defaultValue = "0") int menuItemIndex,
                        HttpServletResponse response, ModelMap modelMap) {
        GeneratedMenu generatedMenu = new GeneratedMenuForAdding();

        if (menuItemIndex > 0 && menuItemIndex <= generatedMenu.generatedMenu.size()) {
            MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(menuItemIndex - 1);
            int index = menuItemClassType.getIndex();

            String classSimpleName = menuItemClassType.getClassType().getSimpleName();
            CookieUtil.addCookie(ITEM_CLASS_TYPE, classSimpleName, response);
            String randValue = "false";
            if (index % 2 == 0) { //add random item
                randValue = "true";
            }
            CookieUtil.addCookie(IS_RANDOM, randValue, response);

            return new ModelAndView("redirect:/" + Constants.ADD_ITEM_TITLE, modelMap);
        }
        return new ModelAndView(Constants.ADD_MENU_TITLE, modelMap);
    }
}
