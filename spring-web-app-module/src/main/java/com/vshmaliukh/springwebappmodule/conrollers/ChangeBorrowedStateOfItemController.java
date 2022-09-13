package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

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
                       @RequestParam String indexOfItem, // TODO use int value
                       ModelMap model) {
//        model.addAttribute(USER_NAME, userName);
//        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);

//        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        try {
            changeItemBorrowedState(indexOfItem, userAtr);
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(CHANGE_ITEM_BORROWED_STATE, nfe);
        }
//        finally {
//            UrlUtil.redirectTo(EDIT_ITEMS_TITLE, response, userAtr);
//        }
        return new ModelAndView("redirect:/" + EDIT_ITEMS_TITLE, model);
    }

    // todo
    private void changeItemBorrowedState(String indexOfItem, Map<String, String> userAtr) {
        int index = Integer.parseInt(indexOfItem);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            webShelfHandler.readShelfItems();
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            webShelfHandler.changeBorrowedStateOfItem(allLiteratureObjects, index);
        }
    }
}
