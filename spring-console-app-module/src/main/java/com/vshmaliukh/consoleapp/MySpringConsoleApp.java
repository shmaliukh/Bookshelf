package com.vshmaliukh.consoleapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MySpringConsoleApp implements CommandLineRunner {

    final SpringConsoleUI consoleUI;

    public MySpringConsoleApp(SpringConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public void run(String... args) {
        consoleUI.startWork(true);
    }
}
