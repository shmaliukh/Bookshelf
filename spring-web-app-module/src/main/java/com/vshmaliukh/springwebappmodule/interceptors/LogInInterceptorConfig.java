package com.vshmaliukh.springwebappmodule.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@Component
public class LogInInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    LogInInterceptor logInInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/" + LOG_IN_TITLE);
    }

}
