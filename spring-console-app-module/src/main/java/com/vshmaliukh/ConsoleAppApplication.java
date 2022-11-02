package com.vshmaliukh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.spring_shelf_core",
        "com.vshmaliukh.console_app",
})
public class ConsoleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleAppApplication.class, args);
    }

}
