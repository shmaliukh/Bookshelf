package com.vshmaliukh.springwebappmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.springbootshelfcore",
        "com.vshmaliukh.springwebappmodule",
})
public class SpringWebAppModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppModuleApplication.class, args);
    }

}