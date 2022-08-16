package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.shelf_handler.ShelfHandlerInterface;
import org.vshmaliukh.shelf.shelf_handler.User;

import static org.vshmaliukh.shelf.shelf_handler.AbstractSaveReadAbleShelfHandler.*;

public class WebUserHandler extends SqlLiteShelfHandler {

    User user;
    int typeOfWorkWithFiles;
    ShelfHandlerInterface shelfHandler;

    public WebUserHandler(String userName, int typeOfWorkWithFiles) {
        super(userName);
        this.shelf = new Shelf();
        configShelfHandler();
    }

    public ShelfHandlerInterface getShelfHandler() {
        return shelfHandler;
    }

    void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                shelfHandler = new WebShelfHandler(HOME_PROPERTY, user.getName());
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
