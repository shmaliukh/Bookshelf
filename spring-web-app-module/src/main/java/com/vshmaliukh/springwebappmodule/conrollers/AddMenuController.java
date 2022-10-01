package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + ADD_MENU_TITLE)
public class AddMenuController {

    public static final String GENERATED_MENU = "generatedMenu";

    @GetMapping()
    ModelAndView doGet(ModelMap modelMap) {
        List<MenuItemClassType> menuItems = new GeneratedMenuForAdding().getMenuItems();
        ControllerUtils.formRadioButtons(menuItems, modelMap);
        return new ModelAndView(ADD_MENU_TITLE, modelMap);
    }

    @PostMapping()
    ModelAndView doPost(@RequestParam(defaultValue = "0") int menuItemIndex,
                        HttpServletResponse response, ModelMap modelMap) {
        GeneratedMenu generatedMenu = new GeneratedMenuForAdding();

        if (menuItemIndex > 0 && menuItemIndex <= generatedMenu.getMenuItems().size()) {
            MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(menuItemIndex - 1);
            int index = menuItemClassType.getIndex();

            String classSimpleName = menuItemClassType.getClassType().getSimpleName();
            CookieUtil.addCookie(ITEM_CLASS_TYPE, classSimpleName, response);
            String randValue = "false";
            if (index % 2 == 0) { //add random item
                randValue = "true";
            }
            CookieUtil.addCookie(IS_RANDOM, randValue, response);

            return new ModelAndView("redirect:/" + ADD_ITEM_TITLE, modelMap);
        }
        return new ModelAndView(ADD_MENU_TITLE, modelMap);
    }
}
