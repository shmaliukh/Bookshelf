package com.vshmaliukh.spring_web_app_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.spring_shelf_core",
        "com.vshmaliukh.spring_web_app_module",
})
public class SpringWebAppModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppModuleApplication.class, args);
    }

}