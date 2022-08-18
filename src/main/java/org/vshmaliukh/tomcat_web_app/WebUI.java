package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.AbstractUI;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.MySqlShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlLiteShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.User;

import static org.vshmaliukh.console_terminal_app.SaveReadShelfHandler.FILE_MODE_WORK_WITH_MYSQL;
import static org.vshmaliukh.console_terminal_app.SaveReadShelfHandler.FILE_MODE_WORK_WITH_SQLLITE;

public class WebUI extends AbstractUI {

    public WebUI(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;
        configShelfHandler();
    }

    @Override
    public void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_SQLLITE:
                shelfHandler = new SqlLiteShelfHandler(user.getName());
                break;
            case FILE_MODE_WORK_WITH_MYSQL:
                shelfHandler = new MySqlShelfHandler(user.getName());
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
        }
    }
}
