package com.vshmaliukh.spring_web_app_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.spring_shelf_core",
        "com.vshmaliukh.spring_web_app_module",
})
public class SpringWebApplication {

    public static final String LOG_IN_VIA_USER_MODEL = "log_in_via_user_model";

    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class, args);
    }

}