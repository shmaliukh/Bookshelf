package com.vshmaliukh.springwebappmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebAppModuleApplication {

    public static void main(String[] args) {
        if(System.getenv("type") == "mysql"){
            System.setProperty("spring.active.profile","sqlite");
        }
        SpringApplication.run(SpringWebAppModuleApplication.class, args);
    }

}