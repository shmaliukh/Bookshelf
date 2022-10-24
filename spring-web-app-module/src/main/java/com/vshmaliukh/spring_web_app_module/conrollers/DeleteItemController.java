package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.SaveReadShelfHandler;

@Controller
@RequestMapping("/" + Constants.DELETE_ITEM_TITLE)
public class DeleteItemController {

    final SpringBootWebUtil springBootWebUtil;

    public DeleteItemController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam int indexOfItem,
                       ModelMap modelMap) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        if (webShelfHandler != null) {
            webShelfHandler.deleteItemByIndex(indexOfItem);
        } else {
            MyLogUtil.logWarn(this, "userName: '{}' // type of work: '{}' // problem to delete item by '{}' index: " +
                    "webShelfHandler == null", userName, typeOfWork, indexOfItem);
            MyLogUtil.logDebug(this, "doGet(userName: '{}', typeOfWork: '{}',indexOfItem: '{}', modelMap: '{}') // springBootWebUtil: '{}'",
                    userName, typeOfWork, indexOfItem, modelMap, springBootWebUtil);
        }
        return new ModelAndView("redirect:/" + Constants.EDIT_ITEMS_TITLE, modelMap);
    }

}
