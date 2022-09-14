package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;

@Controller
public class ChooseTypeOfWorkController {

    @GetMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
    ModelAndView doGet(ModelMap modelMap) {
        // TODO add implementation code

        String generatedHtmlStr = "";
        modelMap.addAttribute(GENERATED_HTML_STR, generatedHtmlStr);
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }

    @PostMapping("/" + CHOOSE_TYPE_OF_WORK_TITLE)
    ModelAndView doPost(ModelMap modelMap) {
        // TODO add implementation code

        return new ModelAndView(
//                "redirect:/" +
                BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }
}
