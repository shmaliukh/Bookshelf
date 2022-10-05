package com.vshmaliukh.springwebappmodule.shelf.mysql;


import com.vshmaliukh.springwebappmodule.shelf.DbConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "db-config.mysql")
@EnableJpaRepositories(
        basePackages = "com.vshmaliukh.springwebappmodule.shelf.mysql.repositories",
        entityManagerFactoryRef = "mySqlEntityManager")
public class MySqlHandlerConfigurator extends DbConfig {

    @Bean
    @Primary
    public DataSource mySqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUrl(getUrl());
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mySqlEntityManager(@Qualifier("mySqlDataSource") DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.vshmaliukh.springwebappmodule.shelf.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

}
