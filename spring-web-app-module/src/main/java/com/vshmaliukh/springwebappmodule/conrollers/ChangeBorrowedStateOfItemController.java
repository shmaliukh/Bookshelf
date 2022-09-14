package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.CHANGE_ITEM_BORROWED_STATE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.EDIT_ITEMS_TITLE;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.generateShelfHandler;

@Controller
public class ChangeBorrowedStateOfItemController {

    @GetMapping("/" + CHANGE_ITEM_BORROWED_STATE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam int indexOfItem,
                       ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            webShelfHandler.readShelfItems();
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            webShelfHandler.changeBorrowedStateOfItem(allLiteratureObjects, indexOfItem);
        }
        return new ModelAndView("redirect:/" + EDIT_ITEMS_TITLE, model);
    }

}
