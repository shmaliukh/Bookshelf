package com.vshmaliukh.springwebappmodule;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtil {

    private CookieUtil(){}

    public static void addCookie(String name, String value, HttpServletResponse response) {
        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(true);
            response.addCookie(cookie);
        }
    }

}
