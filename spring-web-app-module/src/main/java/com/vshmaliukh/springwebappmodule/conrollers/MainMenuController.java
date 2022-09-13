package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import java.util.Map;

import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.MAIN_MENU_TITLE;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

@Controller
public class MainMenuController {

    @GetMapping("/" + MAIN_MENU_TITLE)
    public ModelAndView doGet(@CookieValue(value = USER_NAME) String userName,
                              @CookieValue(value = TYPE_OF_WORK_WITH_FILES) int typeOfWork,
                              ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        model.addAttribute("typeOfWorkFriendlyString", typeOfWorkMap.get(model.getAttribute(TYPE_OF_WORK_WITH_FILES)));

        String generatedMenuHtml = HtmlUtil.initMainMenu(userAtr) + WebUtils.generateCurrentStateOfShelf(userAtr, ItemTitles.TITLE_LIST);
        model.addAttribute("generatedMenu", generatedMenuHtml);

        return new ModelAndView(MAIN_MENU_TITLE, model);
    }

}
