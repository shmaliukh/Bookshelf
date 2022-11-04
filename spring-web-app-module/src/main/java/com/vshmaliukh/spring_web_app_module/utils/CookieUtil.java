package com.vshmaliukh.spring_web_app_module.utils;

import org.apache.commons.lang3.StringUtils;
import org.vshmaliukh.MyLogUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtil {

    public static final int MAX_AGE = 7 * 24 * 60 * 60;

    private CookieUtil() {
    }

    public static void refreshCookie(Cookie cookie, int timeToAlive) {
        cookie.setMaxAge(timeToAlive);
        MyLogUtil.logDebug(CookieUtil.class, "refreshed cookie: '{}' // set time to alive: '{}'", cookie, timeToAlive);
    }

    public static void addCookie(String name, String value, HttpServletResponse response) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(MAX_AGE);
            cookie.setSecure(true);
            response.addCookie(cookie);
            MyLogUtil.logDebug(CookieUtil.class, "added cookie: '{}'", cookie);
        } else {
            MyLogUtil.logWarn(CookieUtil.class, "problem to save cookie: name or value is blank");
            MyLogUtil.logDebug(CookieUtil.class, "addCookie(name: '{}', value: '{}', response: '{}')", name, value, response);
        }
    }

}
