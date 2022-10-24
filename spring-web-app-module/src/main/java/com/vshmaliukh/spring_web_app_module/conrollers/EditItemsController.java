package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.vshmaliukh.Constants.EDIT_ITEMS_TITLE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_SAVE_READ_SERVICE;

@Controller
@RequestMapping("/" + EDIT_ITEMS_TITLE)
public class EditItemsController {

    final SpringBootWebUtil springBootWebUtil;

    public EditItemsController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap modelMap) {
        springBootWebUtil.formCurrentStateTable(userName, typeOfWork, modelMap);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, ControllerUtils.getFriendlyTypeOfWorkStr(typeOfWork));
        return new ModelAndView(EDIT_ITEMS_TITLE, modelMap);
    }

}
