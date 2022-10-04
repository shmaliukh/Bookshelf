package com.vshmaliukh.springwebappmodule.shelf;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "my-db-config")
@Configuration
@Data
public class MyDbsConfiguration {
    private DataSourceProperties sqliteSourceProperties;
    private DataSourceProperties mysqlSourceProperties;

    private String type;
    private String user;
    private String password;
    private String url;


    @ConfigurationProperties(prefix = "mysql-db-config.mysql")
    public DataSourceProperties getMysqlSourceProperties() {
        return mysqlSourceProperties;
    }

}
