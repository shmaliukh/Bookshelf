package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
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

    final SpringBootWebUtil springBootWebUtil;

    public MainMenuController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping()
    public ModelAndView doGet(@CookieValue(value = USER_NAME) String userName,
                              @CookieValue(value = TYPE_OF_WORK_WITH_SAVE_READ_SERVICE) int typeOfWork,
                              ModelMap modelMap) {
        springBootWebUtil.formCurrentStateTable(userName, typeOfWork, modelMap);
        modelMap.addAttribute(USER_NAME, userName);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, ControllerUtils.getFriendlyTypeOfWorkStr(typeOfWork));
        return new ModelAndView(MAIN_MENU_TITLE, modelMap);
    }

}
