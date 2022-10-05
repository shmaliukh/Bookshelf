package com.vshmaliukh.springwebappmodule.shelf;

import org.vshmaliukh.WebUI;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;

public class SpringBootUI extends WebUI {

    public SpringBootUI(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
    }

    @Override
    public void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLLITE:
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                shelfHandler = new SpringBootSqlShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
        }
    }
}
