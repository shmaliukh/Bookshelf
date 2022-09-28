package com.vshmaliukh.springwebappmodule.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.vshmaliukh.Constants.*;

@Component
@Slf4j
public class LogInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        Object userNameAtr = session.getAttribute(USER_NAME);
        Object typeOfWorkAtr = session.getAttribute(TYPE_OF_WORK_WITH_FILES);
        boolean isValid = userNameAtr != null && typeOfWorkAtr != null;
        if(!isValid){
            try {
                response.sendRedirect(LOG_IN_TITLE);
            } catch (IOException ioe) {
                log.error("[Interceptor] Error: " + ioe.getMessage(), ioe);
            }
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) {
    }
}

