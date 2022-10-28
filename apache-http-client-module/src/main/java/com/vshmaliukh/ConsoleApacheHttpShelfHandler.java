package com.vshmaliukh;

import org.vshmaliukh.console_terminal_app.ConsoleSqlShelfHandler;

import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleApacheHttpShelfHandler extends ConsoleSqlShelfHandler {

    public ConsoleApacheHttpShelfHandler(Scanner scanner, PrintWriter printWriter, String userName, int typeOfWorkWithSql) {
        super(scanner, printWriter, userName, typeOfWorkWithSql);
        initApacheHttpService(userName);
    }

    protected void initApacheHttpService(String userName){
        sqlItemHandler = new ApacheHttpShelfService(userName);
    }

}
