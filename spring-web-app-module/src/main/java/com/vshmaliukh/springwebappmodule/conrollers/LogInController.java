package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.utils.CookieUtil;
import com.vshmaliukh.springwebappmodule.UserModel;
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

    public static final String GENERATED_TYPE_OF_WORK_RADIO_BUTTONS = "generatedTypeOfWorkRadioButtons";

    @GetMapping(value = {"/", "/" + LOG_IN_TITLE})
    ModelAndView doGet(@CookieValue(defaultValue = "") String userName,
                       @CookieValue(defaultValue = "") String typeOfWork,
                       ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        model.addAttribute(GENERATED_TYPE_OF_WORK_RADIO_BUTTONS, ControllerUtils.generateTypeOfWorkRadioButtons(typeOfWork));
        return new ModelAndView(LOG_IN_TITLE, model);
    }

    @PostMapping("/" + LOG_IN_TITLE)
    String doPost(@RequestBody UserModel userModel,
                  HttpServletResponse response) {
        CookieUtil.addCookie(USER_NAME, userModel.getUserName(), response);
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, userModel.getTypeOfWorkAsStr(), response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

}
