package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + MAIN_MENU_TITLE)
public class MainMenuController {

    @GetMapping()
    public ModelAndView doGet(@CookieValue(value = USER_NAME) String userName,
                              @CookieValue(value = TYPE_OF_WORK_WITH_FILES) int typeOfWork,
                              ModelMap modelMap) {
        ControllerUtils.formCurrentStateTable(userName, typeOfWork, modelMap);

        modelMap.addAttribute(USER_NAME, userName);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, ControllerUtils.getFriendlyTypeOfWorkStr(typeOfWork));
        return new ModelAndView(MAIN_MENU_TITLE, modelMap);
    }

}
