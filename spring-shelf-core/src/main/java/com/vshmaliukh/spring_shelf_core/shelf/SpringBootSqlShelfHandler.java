package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.mysql.MysqlSpringBootHandler;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.SqliteSpringBootHandler;
import org.springframework.stereotype.Component;
import org.vshmaliukh.BaseAppConfig;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.data_service.sql_handler.SqliteHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

@Component
public class SpringBootSqlShelfHandler extends SqlShelfHandler {

    MysqlSpringBootHandler mysqlSpringBootHandler;
    SqliteSpringBootHandler sqliteSpringBootHandler;

    public SpringBootSqlShelfHandler(MysqlSpringBootHandler mysqlSpringBootHandler,
                                     SqliteSpringBootHandler sqliteSpringBootHandler) {
        this.mysqlSpringBootHandler = mysqlSpringBootHandler;
        this.sqliteSpringBootHandler = sqliteSpringBootHandler;
    }

    @Override
    public void setUpDataService(String userName, int typeOfWorkWithFiles) {
        switch (typeOfWorkWithFiles) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
                sqliteSpringBootHandler.setUserName(userName);
                sqliteSpringBootHandler.setUpSettings();
                sqlItemHandler = sqliteSpringBootHandler;
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                mysqlSpringBootHandler.setUserName(userName);
                mysqlSpringBootHandler.setUpSettings();
                sqlItemHandler = mysqlSpringBootHandler;
                break;
            default:
                sqlItemHandler = new SqliteHandler(BaseAppConfig.HOME_PROPERTY, userName);
                break;
        }
    }

}