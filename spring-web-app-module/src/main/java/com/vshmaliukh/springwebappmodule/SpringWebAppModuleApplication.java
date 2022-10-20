package com.vshmaliukh.springwebappmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.springbootshelfcore.shelf" ,
        "com.vshmaliukh.springbootshelfcore.shelf.convertors",
        "com.vshmaliukh.springbootshelfcore.shelf.entities",
        "com.vshmaliukh.springbootshelfcore.shelf.mysql",
        "com.vshmaliukh.springbootshelfcore.shelf.repository_services",
        "com.vshmaliukh.springbootshelfcore.shelf.sqlite",
        "com.vshmaliukh.*"})
public class SpringWebAppModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppModuleApplication.class, args);
    }

}