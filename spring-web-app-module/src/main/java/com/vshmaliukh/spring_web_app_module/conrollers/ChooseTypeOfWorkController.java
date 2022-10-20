package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import com.vshmaliukh.spring_web_app_module.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static org.vshmaliukh.Constants.CHOOSE_TYPE_OF_WORK_TITLE;
import static org.vshmaliukh.Constants.MAIN_MENU_TITLE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;

@Controller
@RequestMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
public class ChooseTypeOfWorkController {

    @GetMapping()
    ModelAndView doGet(@CookieValue(value = TYPE_OF_WORK_WITH_FILES, defaultValue = "") String typeOfWork,
                       ModelMap modelMap) {
        ControllerUtils.formRadioButtonsToChooseTypeOfWork(typeOfWork, modelMap);
        return new ModelAndView(CHOOSE_TYPE_OF_WORK_TITLE, modelMap);
    }

    @PostMapping()
    String doPost(@RequestParam(value = TYPE_OF_WORK_WITH_FILES) String typeOfWork,
                        HttpServletResponse response) {
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, typeOfWork, response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

}
