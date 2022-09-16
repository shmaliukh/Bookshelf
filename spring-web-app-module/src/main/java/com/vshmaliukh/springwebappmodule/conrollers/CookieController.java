package com.vshmaliukh.springwebappmodule.conrollers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class CookieController {

    public static final String COOKIE_TITLE = "cookie";
    public static final String PAGE_TO_REDIRECT = "pageToRedirect";

    @GetMapping("/" + COOKIE_TITLE)
    ModelAndView doGet(@RequestParam Map<String, String> paramMap,
                       HttpServletResponse response, ModelMap model) {

        String pageToRedirect = paramMap.getOrDefault(PAGE_TO_REDIRECT, "");
        paramMap.remove(PAGE_TO_REDIRECT, pageToRedirect);
        paramMap.forEach((k, v) -> addCookie(k,v, response)); // TODO create another implementation

        return new ModelAndView("redirect:/" + pageToRedirect, model);
    }

    public static void addCookie(String name, String value, HttpServletResponse response) {
        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(true);
            response.addCookie(cookie);
        }
    }

}
