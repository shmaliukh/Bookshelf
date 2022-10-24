package com.vshmaliukh.spring_web_app_module.interceptors;

import com.vshmaliukh.spring_web_app_module.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.vshmaliukh.MyLogUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.vshmaliukh.Constants.*;

@Component
@Slf4j
public class LogInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        boolean isValid = checkIfUserIsLoggedIn(request);
        if (!isValid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String pageToRedirect = LOG_IN_TITLE;
            try {
                response.sendRedirect(pageToRedirect);
            } catch (Exception e) {
                MyLogUtil.logWarn(this, "problem to redirect to '{}' page // ", pageToRedirect);
                MyLogUtil.logErr(this, e);
            }
        }
        return isValid;
    }

    String readCookieValueAndRefresh(Cookie cookie, String nameToCheck) {
        String cookieName = cookie.getName();
        if (cookieName.equals(nameToCheck)) {
            CookieUtil.refreshCookie(cookie, CookieUtil.MAX_AGE);
            return cookie.getValue();
        }
        MyLogUtil.logWarn(this, "problem to read cookie value by name: '{}' ", nameToCheck);
        MyLogUtil.logDebug(this, "readCookieValueAndRefresh(cookie: '{}', nameToCheck: '{}') will return NULL", cookie, nameToCheck);
        return null;
    }

    boolean checkIfUserIsLoggedIn(HttpServletRequest request) {
        String userName = null;
        String typeOfWork = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                userName = readCookieValue(USER_NAME, userName, cookie);
                typeOfWork = readCookieValue(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, typeOfWork, cookie);
            }
        } else {
            MyLogUtil.logDebug(this, "cookies is null // request: '{}' ", request);
        }
        return userName != null && typeOfWork != null;
    }

    private String readCookieValue(String name, String value, Cookie cookie) {
        if (StringUtils.isBlank(value)) {
            value = readCookieValueAndRefresh(cookie, name);
            MyLogUtil.logDebug(this, "read '{}' value from cookie: '{}' // result: '{}'", name.toUpperCase(), cookie, value);
        }
        return value;
    }

}

