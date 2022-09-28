package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.utils.WebUtils;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static org.vshmaliukh.Constants.*;

@Controller
public class SortingTypesMenuController {

    @PostMapping("/" + Constants.SORTING_TYPES_MENU_TITLE)
    public ModelAndView doPost(@RequestParam String menuItemIndex,
                               ModelMap modelMap) {
        if (StringUtils.isNotBlank(menuItemIndex)) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();

                modelMap.addAttribute(ITEM_CLASS_TYPE, classType);
                return new ModelAndView("redirect:/" + Constants.ITEMS_SORTING_MENU_TITLE, modelMap);
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(Constants.SORTING_TYPES_MENU_TITLE, nfe);
            }
        }
        return new ModelAndView(Constants.SORTING_TYPES_MENU_TITLE, modelMap);
    }

    @GetMapping("/" + Constants.SORTING_TYPES_MENU_TITLE)
    public ModelAndView doGet(ModelMap modelMap) {
        modelMap.addAttribute(GENERATED_HTML_STR, generatePageHtmlText());
        modelMap.addAttribute(GENERATED_TITTLE, "Sort by type");
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }

    private static String generatePageHtmlText() {
        StringBuilder sb = new StringBuilder();
        String generatedMenu = ControllerUtils.generateRadioButtonsMenuHtmlStr(new GeneratedMenuForSorting().generatedMenu);
        sb.append(
                divContainer(
                        htext("Choose type of items to sort", "2") + split() +
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
