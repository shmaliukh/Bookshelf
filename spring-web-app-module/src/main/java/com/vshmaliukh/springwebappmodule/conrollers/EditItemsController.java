package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.vshmaliukh.Constants.EDIT_ITEMS_TITLE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;

@Controller
@RequestMapping("/" + EDIT_ITEMS_TITLE)
public class EditItemsController {

    ControllerUtils controllerUtils;

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap modelMap) {
        controllerUtils.formCurrentStateTable(userName, typeOfWork, modelMap);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, ControllerUtils.getFriendlyTypeOfWorkStr(typeOfWork));
        return new ModelAndView(EDIT_ITEMS_TITLE, modelMap);
    }

    @Autowired
    public void setControllerUtils(ControllerUtils controllerUtils) {
        this.controllerUtils = controllerUtils;
    }

}
