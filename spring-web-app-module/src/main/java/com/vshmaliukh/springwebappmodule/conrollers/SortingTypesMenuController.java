package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.utils.WebUtils;

import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static org.vshmaliukh.Constants.*;

@Controller
public class SortingTypesMenuController {

    @PostMapping("/" + Constants.SORTING_TYPES_MENU_TITLE)
    public ModelAndView doPost(@RequestParam String menuItemIndex,
                               ModelMap model) {
        if (menuItemIndex != null && !menuItemIndex.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();

                model.addAttribute(ITEM_CLASS_TYPE, classType);
                return new ModelAndView("redirect:/" + Constants.ITEMS_SORTING_MENU_TITLE, model);
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(Constants.SORTING_TYPES_MENU_TITLE, nfe);
            }
        }
        return new ModelAndView(Constants.SORTING_TYPES_MENU_TITLE, model);
    }

    @GetMapping("/" + Constants.SORTING_TYPES_MENU_TITLE)
    public ModelAndView doGet(@CookieValue String userName,
                              @CookieValue int typeOfWork,
                              ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);

        model.addAttribute("generatedHtmlStr", generatePageHtmlText());
        return new ModelAndView(Constants.SORTING_TYPES_MENU_TITLE, model);
    }

    private static String generatePageHtmlText() {
        StringBuilder sb = new StringBuilder();
        String generatedMenu = ControllerUtils.generateRadioButtonsMenuHtmlStr(new GeneratedMenuForSorting().generatedMenu);
        sb.append(
                divContainer(
                        description("Choose type of items to sort") + split() +
                                form(SORTING_TYPES_MENU_TITLE, "post",
                                        div(generatedMenu),
                                        formSubmitButton()
                                ) + split() +
                                buttonWithRef("Back", MAIN_MENU_TITLE)
                )
        );
        return sb.toString();
    }

}
