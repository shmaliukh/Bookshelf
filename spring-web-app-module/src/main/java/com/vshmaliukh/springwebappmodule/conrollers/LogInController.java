package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.UserDataModelForJson;
import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    @Qualifier("sqliteJdbcTemplate")
    private JdbcTemplate sqliteTemplate;

    @GetMapping(value = {"/", "/" + LOG_IN_TITLE})
    ModelAndView doGet(@CookieValue(defaultValue = "") String userName,
                       @CookieValue(defaultValue = "") String typeOfWork,
                       ModelMap modelMap) {
        System.out.println(sqliteTemplate.isIgnoreWarnings());
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
