package com.vshmaliukh.console_app;

import com.vshmaliukh.ConsoleApacheHttpShelfHandler;
import com.vshmaliukh.spring_shelf_core.shelf.SpringBootSqlShelfHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.vshmaliukh.console_terminal_app.ConsoleGsonShelfHandler;
import org.vshmaliukh.console_terminal_app.ConsoleSqlShelfHandler;
import org.vshmaliukh.console_terminal_app.ConsoleUI;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForUser;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.Scanner;

@Component
@ComponentScan(basePackages = {"com.vshmaliukh.spring_shelf_core.shelf"})
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
        switch (saveReadServiceType) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new ConsoleGsonShelfHandler(scanner, printWriter, user.getName(), saveReadServiceType);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                shelfHandler = springBootSqlShelfHandler;
                shelfHandler.setUpDataService(user.getName(), saveReadServiceType);
                break;
            case SaveReadShelfHandler.OLD_MODE_WORK_WITH_SQLITE:
            case SaveReadShelfHandler.OLD_MODE_WORK_WITH_MYSQL:
                shelfHandler = new ConsoleSqlShelfHandler(scanner, printWriter, user.getName(), saveReadServiceType);
                break;
            case SaveReadShelfHandler.APACHE_HTTP_MODE_WORK:
                shelfHandler = new ConsoleApacheHttpShelfHandler(scanner, printWriter, user.getName(), saveReadServiceType);
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), saveReadServiceType);
                break;
        }
    }

    @Override
    public void informAboutFileTypeWork(int typeOfWorkWithFiles) {
        printWriter.println("Type of work with save/read shelf with files: ");
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                printWriter.println("FILE_MODE_WORK_WITH_FILE_PER_TYPE");
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
                printWriter.println("MODE_WORK_WITH_SQLITE");
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                printWriter.println("MODE_WORK_WITH_MYSQL");
                break;
            case SaveReadShelfHandler.OLD_MODE_WORK_WITH_SQLITE:
                printWriter.println("OLD_MODE_WORK_WITH_SQLITE");
                break;
            case SaveReadShelfHandler.OLD_MODE_WORK_WITH_MYSQL:
                printWriter.println("OLD_MODE_WORK_WITH_MYSQL");
                break;
            case SaveReadShelfHandler.APACHE_HTTP_MODE_WORK:
                printWriter.println("APACHE_HTTP_MODE_WORK");
                break;
            default:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
        }
    }

}
