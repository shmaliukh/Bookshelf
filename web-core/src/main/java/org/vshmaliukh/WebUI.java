package org.vshmaliukh;

import lombok.NoArgsConstructor;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.AbstractUI;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.User;

@NoArgsConstructor
public class WebUI extends AbstractUI {

    public WebUI(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.saveReadServiceType = typeOfWorkWithFiles;
        configShelfHandler();
    }

    @Override
    public void configShelfHandler() {
        switch (saveReadServiceType) {
            case SaveReadShelfHandler.MODE_WORK_WITH_ONE_FILE:
            case SaveReadShelfHandler.MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new GsonShelfHandler(user.getName(), saveReadServiceType);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                shelfHandler = new SqlShelfHandler(user.getName(), saveReadServiceType);
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), saveReadServiceType);
                break;
        }
    }
}
