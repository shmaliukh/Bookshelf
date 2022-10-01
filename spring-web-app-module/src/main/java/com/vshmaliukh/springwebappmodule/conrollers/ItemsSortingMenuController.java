package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + ITEMS_SORTING_MENU_TITLE)
public class ItemsSortingMenuController {

    @PostMapping()
    ModelAndView doPost(@RequestParam String menuItemIndex,
                        @RequestParam String itemClassType,
                        ModelMap modelMap) {
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        modelMap.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        return new ModelAndView("redirect:/" + ITEMS_SORTING_MENU_TITLE, modelMap);
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(defaultValue = "") String menuItemIndex,
                       @RequestParam(defaultValue = "") String itemClassType,// TODO rework to use PathVariable
                       ModelMap modelMap) {
        ControllerUtils.formItemTableByClass(userName, typeOfWork, itemClassType, menuItemIndex, modelMap);
        ControllerUtils.formRadioButtonsMapForSortingByClassType(itemClassType, modelMap);
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        modelMap.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        return new ModelAndView(ITEMS_SORTING_MENU_TITLE, modelMap);
    }

}
