package com.vshmaliukh.springbootshelfcore.shelf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.AbstractUI;
import org.vshmaliukh.shelf.shelf_handler.GsonShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

import static org.vshmaliukh.services.SaveReadShelfHandler.*;

@Slf4j
@Service
public class SpringBootUI extends AbstractUI {

    final SpringBootSqlShelfHandler springBootSqlShelfHandler;

    public SpringBootUI(SpringBootSqlShelfHandler springBootSqlShelfHandler) {
        this.springBootSqlShelfHandler = springBootSqlShelfHandler;
    }

    @Override
    public void configShelfHandler() {
        switch (typeOfWorkWithFiles) {
            case MODE_WORK_WITH_ONE_FILE:
            case MODE_WORK_WITH_FILE_PER_TYPE:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
            case MODE_WORK_WITH_SQLITE:
            case MODE_WORK_WITH_MYSQL:
                shelfHandler = springBootSqlShelfHandler;
                shelfHandler.setUpDataService(user.getName(), typeOfWorkWithFiles);
                break;
            case OLD_MODE_WORK_WITH_SQLITE:
            case OLD_MODE_WORK_WITH_MYSQL:
                shelfHandler = new SqlShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
            default:
                shelfHandler = new GsonShelfHandler(user.getName(), typeOfWorkWithFiles);
                break;
        }
        log.debug("[" + this.getClass().getSimpleName() + "] info: user: '{}' // shelfHandler type: '{}'", user.getName(), shelfHandler.getClass().getSimpleName());
    }

}
