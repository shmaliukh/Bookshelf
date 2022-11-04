package com.vshmaliukh.httpclientmodule.http_client_services;

import com.google.gson.Gson;
import org.vshmaliukh.services.save_read_services.sql_handler.AbstractSqlHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;

public abstract class AbstractHttpShelfService extends AbstractSqlHandler implements HttpShelfService {

    protected int typeOfWork;
    protected Gson gson = new Gson();

    protected AbstractHttpShelfService(String userName, int typeOfWork) {
        super(userName);
        this.typeOfWork = typeOfWork;
        init();
        logIn(userName, typeOfWork);
    }

    @Override
    public void setUpSettings() {

    }

    @Override
    public void readUserId(UserContainer user) {

    }

}
