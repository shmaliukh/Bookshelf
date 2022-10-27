package com.vshmaliukh.spring_web_app_module.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(TestForApacheController.TEST)
public class TestForApacheController {

    public static final String TEST = "/test";

    @GetMapping()
    String doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("myHeader", "test_doGet");
        return TEST;
    }

}
