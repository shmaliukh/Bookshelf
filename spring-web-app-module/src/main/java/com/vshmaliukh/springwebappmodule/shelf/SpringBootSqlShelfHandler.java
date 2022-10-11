package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.mysql.MysqlSqlSpringBootHandler;
import org.springframework.stereotype.Component;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.sql_handler.SqliteHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

@Component
public class SpringBootSqlShelfHandler extends SqlShelfHandler {

    final MysqlSqlSpringBootHandler mysqlSpringBootHandler;

    public SpringBootSqlShelfHandler(MysqlSqlSpringBootHandler mysqlSpringBootHandler) {
        this.mysqlSpringBootHandler = mysqlSpringBootHandler;
    }

    @Override
    public void setUpDataService(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
                //TODO
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                mysqlSpringBootHandler.setUserName(userName);
                mysqlSpringBootHandler.setUpSettings();
                sqlItemHandler = mysqlSpringBootHandler;
                break;
            default:
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
        }
    }

}
