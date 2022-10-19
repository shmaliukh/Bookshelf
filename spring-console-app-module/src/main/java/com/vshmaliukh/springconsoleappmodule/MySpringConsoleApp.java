package com.vshmaliukh.springconsoleappmodule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MySpringConsoleApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MySpringConsoleApp.class);


    final SpringConsoleUI consoleUI;

    public MySpringConsoleApp(SpringConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public void run(String... args) throws Exception {
        consoleUI.startWork(true);
    }
}
