package com.vshmaliukh.spring_web_app_module.interceptors;

import com.vshmaliukh.spring_web_app_module.SpringWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@Component
public class LogInInterceptorConfig implements WebMvcConfigurer {

    final LogInInterceptor logInInterceptor;

    public LogInInterceptorConfig(LogInInterceptor logInInterceptor) {
        this.logInInterceptor = logInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/" + LOG_IN_TITLE,
                        "/" + SpringWebApplication.LOG_IN_VIA_USER_MODEL,
                        "/utils.js",
                        "/ping/**");
    }

}
