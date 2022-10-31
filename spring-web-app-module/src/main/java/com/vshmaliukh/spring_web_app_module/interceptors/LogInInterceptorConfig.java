package com.vshmaliukh.spring_web_app_module.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vshmaliukh.MyLogUtil;

import static com.vshmaliukh.spring_web_app_module.conrollers.AddItemViaCustomHttpClient.ADD_TEST;
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
                .excludePathPatterns("/", "/" + LOG_IN_TITLE, ADD_TEST, "/utils.js", "/test");
        MyLogUtil.logDebug(this, "interceptor '{}' registered ", logInInterceptor);
    }

}
