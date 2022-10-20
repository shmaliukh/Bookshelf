package com.vshmaliukh.springbootshelfcore.shelf;

import org.springframework.stereotype.Service;
import org.vshmaliukh.WebUI;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;


@Service
public class SpringBootUI extends WebUI {

    final SpringBootSqlShelfHandler springBootSqlShelfHandler;

    public SpringBootUI(SpringBootSqlShelfHandler springBootSqlShelfHandler) {
        this.springBootSqlShelfHandler = springBootSqlShelfHandler;
    }

    @Override
    public void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
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
