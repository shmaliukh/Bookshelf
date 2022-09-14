package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;

import java.util.Map;

import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;

@Controller
public class DeleteItemController {

    @GetMapping("/" + Constants.DELETE_ITEM_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam int indexOfItem,
                       ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            webShelfHandler.deleteItemByIndex(indexOfItem);
        }

        return new ModelAndView("redirect:/" + Constants.EDIT_ITEMS_TITLE, model);
    }

}
