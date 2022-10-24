package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;

import java.util.List;

import static org.vshmaliukh.Constants.ITEMS_SORTING_MENU_TITLE;
import static org.vshmaliukh.Constants.SORTING_TYPES_MENU_TITLE;

@Controller
@RequestMapping(path = "/" + SORTING_TYPES_MENU_TITLE)
public class SortingTypesMenuController {

    @PostMapping()
    public ModelAndView doPost(@RequestParam String menuItemIndex,
                               ModelMap modelMap) {
        String pageToRedirect = ITEMS_SORTING_MENU_TITLE;
        if (StringUtils.isNotBlank(menuItemIndex)) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();
                return new ModelAndView("redirect:/" + pageToRedirect + "/" + classType, modelMap);
            } catch (NumberFormatException nfe) {
                MyLogUtil.logErr(this, nfe);
            }
        } else {
            MyLogUtil.logWarn(this, "problem to redirect to '{}' page: menuItemIndex is blank", pageToRedirect);
            MyLogUtil.logDebug(this, "doPost(menuItemIndex: '{}', modelMap: '{}') ", menuItemIndex, modelMap, modelMap);
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
