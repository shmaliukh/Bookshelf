package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogInController {

    public static final String USER_NAME = "user_name";
    public static final String TYPE_OF_WORK_WITH_FILES = "type_of_work_with_files";

    @PostMapping("/login")
    String logIn(Model model){
        model.getAttribute(USER_NAME);
        model.getAttribute(TYPE_OF_WORK_WITH_FILES);

        return "login";
    }
}
