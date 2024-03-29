package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + ITEMS_SORTING_MENU_TITLE + "/{" + ITEM_CLASS_TYPE + "}")
public class ItemsSortingMenuController {

    final SpringBootWebUtil springBootWebUtil;

    public ItemsSortingMenuController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @PostMapping()
    ModelAndView doPost(@RequestParam String menuItemIndex,
                        @PathVariable(name = ITEM_CLASS_TYPE) String itemClassType,
                        ModelMap modelMap) {
        modelMap.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        return new ModelAndView("redirect:/" + ITEMS_SORTING_MENU_TITLE + "/" + itemClassType, modelMap);
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(defaultValue = "") String menuItemIndex,
                       @PathVariable(name = ITEM_CLASS_TYPE) String itemClassType,
                       ModelMap modelMap) {
        springBootWebUtil.formItemTableByClass(userName, typeOfWork, itemClassType, menuItemIndex, modelMap);
        ControllerUtils.formRadioButtonsMapForSortingByClassType(itemClassType, modelMap);
        modelMap.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        return new ModelAndView(ITEMS_SORTING_MENU_TITLE, modelMap);
    }

}
