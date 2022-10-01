package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;

import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;

@Controller
@RequestMapping("/" + Constants.DELETE_ITEM_TITLE)
public class DeleteItemController {

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam int indexOfItem,
                       ModelMap model) {
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userName, typeOfWork);
        if (webShelfHandler != null) {
            webShelfHandler.deleteItemByIndex(indexOfItem);
        }
        return new ModelAndView("redirect:/" + Constants.EDIT_ITEMS_TITLE, model);
    }

}
