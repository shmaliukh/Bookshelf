package com.vshmaliukh.springwebappmodule.shelf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "shelf.db.mysql")
@Data
public class MysqlConfiguration {
    private String url;
    private String user;
    private String password;

}
