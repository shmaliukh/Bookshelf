package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.shelf_handler.ShelfHandlerInterface;
import org.vshmaliukh.shelf.shelf_handler.User;

import static org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler.*;

public class UserInterfaceHandler {

    User user;
    int typeOfWorkWithFiles;
    ShelfHandlerInterface shelfHandler;

    void logIn() {

    }

    void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                //shelfHandler = new ItemGsonHandlerOneFileUser(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                //saveReadUserFilesHandler = new ItemGsonHandlerPerTypeUser(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_SQLLITE:
                shelfHandler = new SqlLiteShelfHandler(user.getName());
                break;
            default:
                shelfHandler = new SqlLiteShelfHandler(user.getName());
                break;
        }
    }

}
