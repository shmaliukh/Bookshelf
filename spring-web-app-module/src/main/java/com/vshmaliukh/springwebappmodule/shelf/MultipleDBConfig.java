package com.vshmaliukh.springwebappmodule.shelf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Profile({"mysql"})
public class MultipleDBConfig {
    @Bean(name = "mysqlDb")
//    @ConfigurationProperties(prefix = "shelf.db.mysql")
    public DataSource mysqlDataSource(MysqlConfiguration mysqlConfiguration) {
        return DataSourceBuilder
                .create()
                .url(mysqlConfiguration.getUrl())
                .username(mysqlConfiguration.getUser())
                .password(mysqlConfiguration.getPassword())
                .build();
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDb") DataSource dsMySQL) {
        return new JdbcTemplate(dsMySQL);
    }

//    @Bean(name = "sqliteDb")
//    @ConfigurationProperties(prefix = "spring.ds-sqlite")
//    public DataSource sqliteDataSource() {
//        return  DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "sqliteJdbcTemplate")
//    public JdbcTemplate sqliteJdbcTemplate(@Qualifier("sqliteDb") DataSource dsSqlite) {
//        return new JdbcTemplate(dsSqlite);
//    }
}
