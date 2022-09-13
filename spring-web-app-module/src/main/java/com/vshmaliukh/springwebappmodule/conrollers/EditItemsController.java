package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.EDIT_ITEMS_TITLE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.MAIN_MENU_TITLE;

@Controller
public class EditItemsController extends HttpServlet {

    @GetMapping("/" + EDIT_ITEMS_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        StringBuilder stringBuilder = new StringBuilder();

        String tableForEditingItems = WebUtils.generateTableForEditingItems(userAtr);
        stringBuilder.append(tableForEditingItems);
        stringBuilder.append(HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE));

        model.addAttribute("generatedHtmlStr", stringBuilder.toString());

        return new ModelAndView(EDIT_ITEMS_TITLE, model);
    }
}
