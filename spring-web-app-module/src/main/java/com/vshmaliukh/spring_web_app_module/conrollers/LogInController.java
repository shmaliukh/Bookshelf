package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.utils.ControllerUtils;
import com.vshmaliukh.spring_web_app_module.utils.CookieUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.MyUtils;
import org.vshmaliukh.UserDataModelForJson;

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
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, typeOfWork, response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

    @PostMapping("/ping/" + LOG_IN_TITLE)
    public ResponseEntity logIn(@RequestBody UserDataModelForJson userModel) {
        ResponseEntity<Object> build = ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,
                        MyUtils.generateCookieValue(USER_NAME, userModel.getUserName()),
                        MyUtils.generateCookieValue(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, userModel.getTypeOfWorkAsStr()))
                .build();
        return build;
    }

}
