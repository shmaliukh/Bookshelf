package com.vshmaliukh.springwebappmodule.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtil {

    public static final int MAX_AGE = 7 * 24 * 60 * 60;

    private CookieUtil(){}

    //todo add method to refresh maxAge

    public static void addCookie(String name, String value, HttpServletResponse response) {
        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(MAX_AGE);
            cookie.setSecure(true);
            response.addCookie(cookie);
        }
    }

}
