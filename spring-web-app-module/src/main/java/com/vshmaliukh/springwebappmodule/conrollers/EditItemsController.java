package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.SpringAppUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

@Controller
public class EditItemsController extends HttpServlet {

    @GetMapping("/" + Constants.EDIT_ITEMS_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        StringBuilder stringBuilder = new StringBuilder();

        String tableForEditingItems = WebUtils.generateTableForEditingItems(userAtr);
        stringBuilder.append(tableForEditingItems);
        stringBuilder.append(HtmlUtil.formHTMLButton(SpringAppUtils.generateUrlString(Constants.MAIN_MENU_TITLE), Constants.MAIN_MENU_TITLE));

        model.addAttribute("generatedHtmlStr", stringBuilder.toString());

        return new ModelAndView(Constants.EDIT_ITEMS_TITLE, model);
    }
}
