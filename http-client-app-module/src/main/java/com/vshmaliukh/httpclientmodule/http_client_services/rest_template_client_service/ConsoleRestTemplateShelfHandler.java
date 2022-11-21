package com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vshmaliukh.console_terminal_app.ConsoleSqlShelfHandler;
import org.vshmaliukh.shelf.Shelf;

import javax.annotation.PostConstruct;

@Component
@NoArgsConstructor
public class ConsoleRestTemplateShelfHandler extends ConsoleSqlShelfHandler {

    RestTemplateClientServiceImp restTemplateClientServiceImp;

    @Autowired
    public ConsoleRestTemplateShelfHandler(RestTemplateClientServiceImp restTemplateClientServiceImp) {
        this.restTemplateClientServiceImp = restTemplateClientServiceImp;
    }

    @PostConstruct
    protected void postConstructInit() {
        this.shelf = new Shelf();
        this.sqlItemHandler = restTemplateClientServiceImp;
    }

    @Override
    public void setUpDataService(String userName, int typeOfWork) {
        restTemplateClientServiceImp.logIn(userName, typeOfWork);
    }

    @Override
    public void saveShelfItems() {

    }

}
