package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.mysql.MysqlSpringBootHandler;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.sql_handler.SqliteHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

public class SpringBootSqlShelfHandler extends SqlShelfHandler {

    public SpringBootSqlShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
    }

    @Override
    public void setUpDataService(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
                //TODO
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                //TODO
                sqlItemHandler = new MysqlSpringBootHandler();
//                BookRepository repository
//                sqlItemHandler = new MysqlBookService();
                break;
            default:
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
        }
    }

}
