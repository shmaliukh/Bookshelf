package com.vshmaliukh.springwebappmodule.shelf;

import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.sql_handler.MySqlHandler;
import org.vshmaliukh.services.file_service.sql_handler.SqliteHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

public class SpringBootSqlShelfHandler extends SqlShelfHandler {

    public SpringBootSqlShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
    }

    @Override
    public void setUpDataSaver(String userName, int typeOfWorkWithFiles) {
        //TODO
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLLITE:
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                sqlItemHandler = new MySqlHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
            default:
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
        }
    }

}
