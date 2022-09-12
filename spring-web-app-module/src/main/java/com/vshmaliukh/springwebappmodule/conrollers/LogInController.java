package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {

    public static final String USER_NAME = "userName";

    @GetMapping("/login")
    String logIn_post(Model model){
        return "login";
    }

    @PostMapping("/login")
    String logInAuthorization(@RequestParam String userName, Model model){
        return "redirect:/login/" + userName;
    }

    @PostMapping("/login/{userNameParam}")
    String logIn(@PathVariable(value = "userNameParam") String userNameParam, Model model){
        model.addAttribute(USER_NAME, userNameParam);
        return "login";
    }

    @GetMapping("/login/{userNameParam}")
    String logIn_get(@PathVariable(value = "userNameParam") String userNameParam, Model model){
        model.addAttribute(USER_NAME, userNameParam);
        return "login";
    }
}
