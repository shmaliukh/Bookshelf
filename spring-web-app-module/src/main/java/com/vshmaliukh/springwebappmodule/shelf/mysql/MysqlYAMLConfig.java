package com.vshmaliukh.springwebappmodule.shelf.mysql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "db-config.mysql")
public class MysqlYAMLConfig extends DataSourceProperties {

    private String hibernateDialect;
//    private String hibernateHbm2ddlAuto;
//    private String hibernateShowSql;
//    private String hibernateGloballyQuotedIdentifiers;

}
