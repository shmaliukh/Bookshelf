package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.DELETE_ITEM_TITLE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.EDIT_ITEMS_TITLE;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.generateShelfHandler;

@Controller
public class DeleteItemController {

    @GetMapping("/" + DELETE_ITEM_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam String indexOfItem, // TODO use int value
                       ModelMap model) {
//        model.addAttribute(USER_NAME, userName);
//        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        try {
            deleteItemByIndex(indexOfItem, userAtr);
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(DELETE_ITEM_TITLE, nfe);
        }
        return new ModelAndView("redirect:/" + EDIT_ITEMS_TITLE, model);
    }

    private void deleteItemByIndex(String indexOfItem, Map<String, String> userAtr) {
        int index = Integer.parseInt(indexOfItem);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            webShelfHandler.deleteItemByIndex(index);
        }
    }
}
