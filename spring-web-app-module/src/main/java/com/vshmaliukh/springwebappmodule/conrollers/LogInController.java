package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.CookieUtil;
import com.vshmaliukh.springwebappmodule.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.GENERATED_HTML_STR;
import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static org.vshmaliukh.Constants.*;
import static org.vshmaliukh.JavaScriptBuilder.*;

@Controller
public class LogInController {

    public static final String FORM_ID = "formElem";

    @GetMapping(value = {"/", "/" + LOG_IN_TITLE})
    ModelAndView doGet(@CookieValue(defaultValue = "") String userName,
                       @CookieValue(defaultValue = "") String typeOfWork,
                       ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);

        model.addAttribute(GENERATED_HTML_STR, generateLogInPageText(userName, typeOfWork));
        return new ModelAndView(LOG_IN_TITLE, model);
    }

    private static String generateLogInPageText(String userName, String typeOfWork) {
        return divContainer(
                cardWhite("Please, log in",
                        form(FORM_ID,
                                description("User name") +
                                        logInInputUserName(USER_NAME, userName) +
                                        split() +
                                        description(ChooseTypeOfWorkController.TYPE_OF_WORK_DESCRIPTION) +
                                        ControllerUtils.generateTypeOfWorkRadioButtons(typeOfWork),
                                formSubmitButton("Sign in")
                        )
                )
        ) + script(formOnSubmit(FORM_ID, sendFormDataAsJson(FORM_ID, LOG_IN_TITLE, "post", MAIN_MENU_TITLE)));
    }

    @PostMapping("/" + LOG_IN_TITLE)
    String doPost(@RequestBody UserModel userModel,
                  HttpServletResponse response) {
        CookieUtil.addCookie(USER_NAME, userModel.getUserName(), response);
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, userModel.getTypeOfWorkAsStr(), response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

}
