package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.utils.WebUtils;

import java.util.List;

import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping(path = "/" + SORTING_TYPES_MENU_TITLE)
public class SortingTypesMenuController {

    @PostMapping()
    public ModelAndView doPost(@RequestParam String menuItemIndex,
                               ModelMap modelMap) {
        if (StringUtils.isNotBlank(menuItemIndex)) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();

                modelMap.addAttribute(ITEM_CLASS_TYPE, classType);
                return new ModelAndView("redirect:/" + ITEMS_SORTING_MENU_TITLE, modelMap);
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(SORTING_TYPES_MENU_TITLE, nfe);
            }
        }
        return new ModelAndView(SORTING_TYPES_MENU_TITLE, modelMap);
    }

    @GetMapping()
    public ModelAndView doGet(ModelMap modelMap) {
        List<MenuItemClassType> menuItems = new GeneratedMenuForSorting().getMenuItems();
        ControllerUtils.formRadioButtons(menuItems, modelMap);
        return new ModelAndView(SORTING_TYPES_MENU_TITLE, modelMap);
    }

}
