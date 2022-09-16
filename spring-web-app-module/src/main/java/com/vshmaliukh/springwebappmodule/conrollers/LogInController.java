package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.GENERATED_HTML_STR;
import static com.vshmaliukh.springwebappmodule.conrollers.CookieController.COOKIE_TITLE;
import static com.vshmaliukh.springwebappmodule.conrollers.CookieController.PAGE_TO_REDIRECT;
import static org.vshmaliukh.Constants.*;

@Controller
public class LogInController {

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
                        form(LOG_IN_TITLE, "post",
                                description("User name") +
                                        logInInputUserName(USER_NAME, userName) +
                                        split() +
                                        description(ChooseTypeOfWorkController.TYPE_OF_WORK_DESCRIPTION) +
                                        ControllerUtils.generateTypeOfWorkRadioButtons(typeOfWork),
                                formSubmitButton("Sign in")
                        )
                )
        );
    }

    @PostMapping("/" + LOG_IN_TITLE)
    ModelAndView doPost(@RequestParam String userName,
                        @RequestParam int typeOfWork,
                        ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        model.addAttribute(PAGE_TO_REDIRECT, MAIN_MENU_TITLE);
        return new ModelAndView("redirect:/" + COOKIE_TITLE, model);
    }

}
