package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_shelf_core.shelf.UserDataModelForJson;
import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import com.vshmaliukh.spring_web_app_module.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static org.vshmaliukh.Constants.*;

@Controller
public class LogInController {

    @GetMapping(value = {"/", "/" + LOG_IN_TITLE})
    ModelAndView doGet(@CookieValue(defaultValue = "") String userName,// Todo save userModel to cookie
                       @CookieValue(defaultValue = "") String typeOfWork,
                       ModelMap modelMap) {
        modelMap.addAttribute(USER_NAME, userName);
        ControllerUtils.formRadioButtonsToChooseTypeOfWork(typeOfWork, modelMap);
        return new ModelAndView(LOG_IN_TITLE, modelMap);
    }

    @PostMapping("/" + LOG_IN_TITLE)
    String doPost(@RequestBody UserDataModelForJson userModel,
                  HttpServletResponse response) {
        String userName = userModel.getUserName();
        String typeOfWork = userModel.getTypeOfWorkAsStr();

        CookieUtil.addCookie(USER_NAME, userName, response);
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, typeOfWork, response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

}
