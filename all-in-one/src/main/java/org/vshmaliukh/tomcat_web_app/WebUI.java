package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.AbstractUI;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.User;

import static org.vshmaliukh.services.SaveReadShelfHandler.*;

public class WebUI extends AbstractUI {

    public WebUI(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;
        configShelfHandler();
    }

    @Override
    public void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case MODE_WORK_WITH_ONE_FILE:
            case MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
            case MODE_WORK_WITH_SQLLITE:
            case MODE_WORK_WITH_MYSQL:
                shelfHandler = new SqlShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
        }
    }
}
