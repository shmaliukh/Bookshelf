package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.vshmaliukh.springwebappmodule.BootstrapHtmlBuilder.*;
import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static com.vshmaliukh.springwebappmodule.conrollers.CookieController.COOKIE_TITLE;
import static com.vshmaliukh.springwebappmodule.conrollers.CookieController.PAGE_TO_REDIRECT;
import static org.vshmaliukh.Constants.MAIN_MENU_TITLE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;

@Controller
public class ChooseTypeOfWorkController {

    public static final String TYPE_OF_WORK_DESCRIPTION = "Choose type of work to save/read information about bookshelf items";

    @GetMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
    ModelAndView doGet(@CookieValue(value = TYPE_OF_WORK_WITH_FILES, defaultValue = "") String typeOfWork,
                       ModelMap modelMap) {
        // TODO add implementation code

        StringBuilder sb = new StringBuilder();
        sb.append(description(TYPE_OF_WORK_DESCRIPTION));
        sb.append(split());
        sb.append(divContainer(
                form(CHOOSE_TYPE_OF_WORK_TITLE, "post", ControllerUtils.generateTypeOfWorkRadioButtons(typeOfWork), formSubmitButton())
        ));

        modelMap.addAttribute(GENERATED_HTML_STR, sb.toString());
        modelMap.addAttribute(GENERATED_TITTLE , "Choose type of work");
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }

    @PostMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
    ModelAndView doPost(@RequestParam Map<String, String> allParams,
                        ModelMap modelMap) {

        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, allParams.get(TYPE_OF_WORK_WITH_FILES));
        modelMap.addAttribute(PAGE_TO_REDIRECT, MAIN_MENU_TITLE);
        return new ModelAndView("redirect:/" + COOKIE_TITLE, modelMap);
    }
}
