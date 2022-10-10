package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.mysql.MysqlSpringBootHandler;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.file_service.sql_handler.SqliteHandler;
import org.vshmaliukh.shelf.shelf_handler.SqlShelfHandler;

@Component
@NoArgsConstructor
public class SpringBootSqlShelfHandler extends SqlShelfHandler {

    MysqlSpringBootHandler mysqlSpringBootHandler;

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
                sqlItemHandler = mysqlSpringBootHandler;
                break;
            default:
                sqlItemHandler = new SqliteHandler(ConfigFile.HOME_PROPERTY, userName);
                break;
        }
    }

    @Autowired
    public void setMysqlSpringBootHandler(MysqlSpringBootHandler mysqlSpringBootHandler) {
        this.mysqlSpringBootHandler = mysqlSpringBootHandler;
    }

}
