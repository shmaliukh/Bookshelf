package com.vshmaliukh.spring_web_app_module.interceptors;

import com.vshmaliukh.spring_web_app_module.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
            try {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect(LOG_IN_TITLE);
            } catch (Exception e) {
                log.error("[Interceptor] Error: " + e.getMessage(), e);
            }
        }
        return isValid;
    }

    String readCookieValueAndRefresh(Cookie cookie, String nameToCheck) {
        String cookieName = cookie.getName();
        if (cookieName.equals(nameToCheck)) {
            cookie.setMaxAge(CookieUtil.MAX_AGE);
            return cookie.getValue();
        }
        return null;
    }

    boolean checkIfUserIsLoggedIn(HttpServletRequest request) {
        String userName = null;
        String typeOfWork = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (userName == null) {
                    userName = readCookieValueAndRefresh(cookie, USER_NAME);
                }
                if (typeOfWork == null) {
                    typeOfWork = readCookieValueAndRefresh(cookie, TYPE_OF_WORK_WITH_FILES);
                }
            }
        }
        return userName != null && typeOfWork != null;
    }

}

