package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import org.vshmaliukh.console_terminal_app.ConsoleSqlShelfHandler;

import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleRestTemplateShelfHandler extends ConsoleSqlShelfHandler {

    public ConsoleRestTemplateShelfHandler(Scanner scanner, PrintWriter printWriter, String userName, int typeOfWork) {
        super(scanner, printWriter, userName, typeOfWork);
    }

    @Override
    public void setUpDataService(String userName, int typeOfWork) {
        this.sqlItemHandler = new RestTemplateClientServiceImp(userName, typeOfWork);
    }

    @Override
    public void saveShelfItems() {

    }

}
