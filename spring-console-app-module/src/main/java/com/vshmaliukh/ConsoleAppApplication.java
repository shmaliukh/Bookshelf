package com.vshmaliukh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.springbootshelfcore",
        "com.vshmaliukh.consoleapp"
})
public class ConsoleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleAppApplication.class, args);
    }

}
