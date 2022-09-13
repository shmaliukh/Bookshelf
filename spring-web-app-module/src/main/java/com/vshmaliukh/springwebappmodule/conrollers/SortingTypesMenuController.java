package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

@Controller
public class SortingTypesMenuController  {

    @PostMapping("/" + SORTING_TYPES_MENU_TITLE)
    public ModelAndView doPost(@RequestParam String userName,
                             @RequestParam int typeOfWork,
                             @RequestParam String menuItemIndex,
                             ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
//        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        if (menuItemIndex != null && !menuItemIndex.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();

                model.addAttribute(ITEM_CLASS_TYPE, classType);
                return new ModelAndView("redirect:/" + ITEMS_SORTING_MENU_TITLE, model);
//                UrlUtil.redirectTo(ITEMS_SORTING_MENU_TITLE, response,
//                        UrlUtil.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, userAtr)
//                                .addParameter(ITEM_CLASS_TYPE, classType));
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(SORTING_TYPES_MENU_TITLE, nfe);
            }
        }
        return new ModelAndView(SORTING_TYPES_MENU_TITLE, model);
    }

    @GetMapping("/" + SORTING_TYPES_MENU_TITLE)
    public ModelAndView doGet(@RequestParam String userName,
                             @RequestParam int typeOfWork,
                             ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");
        stringBuilder.append(HtmlUtil.generateMenuItemsFormHTML(userAtr, SORTING_TYPES_MENU_TITLE, new GeneratedMenuForSorting()));
        stringBuilder.append(HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE));
//        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));
        String generatedHtmlStr = stringBuilder.toString();
        model.addAttribute("generatedHtmlStr", generatedHtmlStr);
        return new ModelAndView(SORTING_TYPES_MENU_TITLE, model);
    }
}
