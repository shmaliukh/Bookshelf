package com.vshmaliukh.spring_shelf_core.shelf;

import com.vshmaliukh.spring_shelf_core.shelf.mysql.MysqlSpringBootHandler;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.SqliteSpringBootHandler;
import org.vshmaliukh.MyLogUtil;
import org.springframework.stereotype.Component;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.save_read_services.sql_handler.SqliteHandler;
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
    public void setUpDataService(String userName, int saveReadServiceType) {
        MyLogUtil.logInfo(this, "userName: '{}' // type of work with save/read service: '{}'", userName, saveReadServiceType);
        switch (saveReadServiceType) {
            case SaveReadShelfHandler.MODE_WORK_WITH_SQLITE:
                sqliteSpringBootHandler.setUserName(userName);
                sqliteSpringBootHandler.setUpSettings();
                MyLogUtil.logDebug(this, "userName: '{}' // use spring Sqlite handler imp: '{}'", userName, sqliteSpringBootHandler);
                sqlItemHandler = sqliteSpringBootHandler;
                break;
            case SaveReadShelfHandler.MODE_WORK_WITH_MYSQL:
                mysqlSpringBootHandler.setUserName(userName);
                mysqlSpringBootHandler.setUpSettings();
                MyLogUtil.logDebug(this, "userName: '{}' // use spring MySql handler imp: '{}'", userName, mysqlSpringBootHandler);
                sqlItemHandler = mysqlSpringBootHandler;
                break;
            default:
                SqliteHandler sqliteHandler = new SqliteHandler(userName);
                MyLogUtil.logWarn(this, "userName: '{}' // use default Sqlite handler imp: '{}'", userName, sqliteHandler);
                sqlItemHandler = sqliteHandler;
                break;
        }
    }

}
