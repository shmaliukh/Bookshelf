package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static org.vshmaliukh.Constants.MAIN_MENU_TITLE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;

@Controller
public class ChooseTypeOfWorkController {

    public static final String TYPE_OF_WORK_DESCRIPTION = "Choose type of work to save/read information about bookshelf items";

    @GetMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
    ModelAndView doGet(@CookieValue(value = TYPE_OF_WORK_WITH_FILES, defaultValue = "") String typeOfWork,
                       ModelMap modelMap) {
        modelMap.addAttribute(GENERATED_HTML_STR, generatePageHtmlText(typeOfWork));
        modelMap.addAttribute(GENERATED_TITTLE, "Choose type of work");
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }

    @PostMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
    String doPost(@RequestParam(value = TYPE_OF_WORK_WITH_FILES) String typeOfWork,
                        HttpServletResponse response) {
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, typeOfWork, response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

    private static String generatePageHtmlText(String typeOfWork) {
        return htext(TYPE_OF_WORK_DESCRIPTION, "2") +
                split() +
                form(CHOOSE_TYPE_OF_WORK_TITLE, "post",
                        ControllerUtils.generateTypeOfWorkRadioButtons(typeOfWork),
                        formSubmitButton()) +
                split() +
                buttonWithRef("Back", MAIN_MENU_TITLE);
    }

}
