package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.SpringAppUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.WebUtils;

import java.util.Map;

import static org.vshmaliukh.Constants.ITEM_CLASS_TYPE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.Constants.USER_NAME;

@Controller
public class SortingTypesMenuController {

    @PostMapping("/" + Constants.SORTING_TYPES_MENU_TITLE)
    public ModelAndView doPost(@RequestParam String userName,
                               @RequestParam int typeOfWork,
                               @RequestParam String menuItemIndex,
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
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");
        stringBuilder.append(HtmlUtil.generateMenuItemsFormHTML(userAtr, Constants.SORTING_TYPES_MENU_TITLE, new GeneratedMenuForSorting()));
        stringBuilder.append(HtmlUtil.formHTMLButton(SpringAppUtils.generateUrlString(Constants.MAIN_MENU_TITLE), Constants.MAIN_MENU_TITLE));
//        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));
        String generatedHtmlStr = stringBuilder.toString();
        model.addAttribute("generatedHtmlStr", generatedHtmlStr);
        return new ModelAndView(Constants.SORTING_TYPES_MENU_TITLE, model);
    }
}
