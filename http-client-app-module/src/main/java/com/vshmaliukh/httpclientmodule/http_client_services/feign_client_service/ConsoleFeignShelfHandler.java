package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vshmaliukh.console_terminal_app.ConsoleSqlShelfHandler;
import org.vshmaliukh.shelf.Shelf;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.Scanner;

@Component
@NoArgsConstructor
public class ConsoleFeignShelfHandler extends ConsoleSqlShelfHandler {

    FeignClientServiceImp feignClientServiceImp;

    @Autowired
    public ConsoleFeignShelfHandler(FeignClientServiceImp feignClientServiceImp) {
        this.feignClientServiceImp = feignClientServiceImp;
    }

    @PostConstruct
    protected void postConstructInit() {
        this.shelf = new Shelf();
        this.sqlItemHandler = feignClientServiceImp;
    }

    public ConsoleFeignShelfHandler(Scanner scanner, PrintWriter printWriter, String userName, int typeOfWork) {
        super(scanner, printWriter, userName, typeOfWork);
    }

    @Override
    public void setUpDataService(String userName, int typeOfWork) {
        feignClientServiceImp.logIn(userName, typeOfWork);
    }

    @Override
    public void saveShelfItems() {
        // todo
    }

}
