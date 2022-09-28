package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.UserModel;
import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.vshmaliukh.Constants.*;

@Controller
public class LogInController {

    public static final String GENERATED_TYPE_OF_WORK_RADIO_BUTTONS = "generatedTypeOfWorkRadioButtons";

    @GetMapping(value = {"/", "/" + LOG_IN_TITLE})
    ModelAndView doGet(@CookieValue(defaultValue = "") String userName,
                       @CookieValue(defaultValue = "") String typeOfWork,
                       HttpServletRequest request, ModelMap model) {
        HttpSession requestSession = request.getSession();
        requestSession.invalidate();

        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        model.addAttribute(GENERATED_TYPE_OF_WORK_RADIO_BUTTONS, ControllerUtils.generateTypeOfWorkRadioButtons(typeOfWork));
        return new ModelAndView(LOG_IN_TITLE, model);
    }

    @PostMapping("/" + LOG_IN_TITLE)
    String doPost(@RequestBody UserModel userModel,
                  HttpServletResponse response, HttpServletRequest request) {
        String userName = userModel.getUserName();
        String typeOfWork = userModel.getTypeOfWorkAsStr();

        CookieUtil.addCookie(USER_NAME, userName, response);
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, typeOfWork, response);

        HttpSession requestSession = request.getSession(true);
        requestSession.setAttribute(USER_NAME, userName);
        requestSession.setAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

}
