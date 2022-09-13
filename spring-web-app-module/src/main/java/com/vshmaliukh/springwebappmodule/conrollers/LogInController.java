package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.LOG_IN_TITLE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.MAIN_MENU_TITLE;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

@Controller
public class LogInController {

    @GetMapping("/")
    String logInAuthorization(ModelMap model) {
        return LOG_IN_TITLE;
    }

    @GetMapping("/" + LOG_IN_TITLE)
    ModelAndView logInAuthorization_get(@RequestParam String userName,
                                        @RequestParam int typeOfWork,
                                        ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        return new ModelAndView(LOG_IN_TITLE, model);
    }

    @PostMapping("/" + LOG_IN_TITLE)
    ModelAndView logInAuthorization_post(@RequestParam String userName,
                                         @RequestParam int typeOfWork,
                                         ModelMap model, HttpServletResponse response) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);

        ModelAndView modelAndView = new ModelAndView("redirect:/" + MAIN_MENU_TITLE, model);
        System.out.println(response.getContentType());
        response.addCookie(new Cookie("userName", userName));
        response.addCookie(new Cookie("typeOfWork", String.valueOf(typeOfWork)));
        return modelAndView;
    }


}
