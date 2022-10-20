package com.vshmaliukh.consoleapp;

import com.vshmaliukh.springbootshelfcore.shelf.SpringBootSqlShelfHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.vshmaliukh.console_terminal_app.ConsoleGsonShelfHandler;
import org.vshmaliukh.console_terminal_app.ConsoleUI;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForUser;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.Scanner;

@Component
@ComponentScan(basePackages = {"com.vshmaliukh.springbootshelfcore.shelf"})
public class SpringConsoleUI extends ConsoleUI {

    final SpringBootSqlShelfHandler springBootSqlShelfHandler;

    public SpringConsoleUI(SpringBootSqlShelfHandler springBootSqlShelfHandler) {
        this.springBootSqlShelfHandler = springBootSqlShelfHandler;
    }

    @PostConstruct
    protected void setUpConsoleUI() {
        scanner = new Scanner(System.in);
        printWriter = new PrintWriter(System.out, true);
        consoleInputHandlerForUser = new ConsoleInputHandlerForUser(scanner, printWriter);
    }

    @Override
    public void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new ConsoleGsonShelfHandler(scanner, printWriter, user.getName(), typeOfWorkWithFiles);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                shelfHandler = springBootSqlShelfHandler;
                shelfHandler.setUpDataService(user.getName(), typeOfWorkWithFiles);
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
        }
    }
}
