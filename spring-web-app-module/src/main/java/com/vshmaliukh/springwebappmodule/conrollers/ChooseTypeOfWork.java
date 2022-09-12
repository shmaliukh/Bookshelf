package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.vshmaliukh.springwebappmodule.conrollers.LogInController.TYPE_OF_WORK;

public class ChooseTypeOfWork {

    @PostMapping("/chooseTypeOfWork/{userNameParam}")
    String logInAuthorization(@PathVariable(value = "userNameParam") String userNameParam, Model model){
        model.addAttribute(TYPE_OF_WORK, userNameParam);
        return "redirect:/choose-type-of-work/" + userNameParam;
    }

    @GetMapping("/chooseTypeOfWork/{userNameParam}")
    String logIn_get(@PathVariable(value = "userNameParam") String userNameParam, Model model) {
        model.addAttribute(TYPE_OF_WORK, userNameParam);
        return "redirect:/choose-type-of-work/" + userNameParam;
    }
}
