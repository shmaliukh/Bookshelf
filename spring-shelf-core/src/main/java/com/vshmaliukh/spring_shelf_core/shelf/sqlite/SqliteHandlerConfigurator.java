package com.vshmaliukh.spring_shelf_core.shelf.sqlite;

import com.vshmaliukh.spring_shelf_core.shelf.DbConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "db-config.sqlite")
@EnableJpaRepositories(
        basePackages = "com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories",
        entityManagerFactoryRef = "sqliteEntityManager",
        transactionManagerRef = "sqliteTransactionManager")
public class SqliteHandlerConfigurator extends DbConfig {

    @Bean
    public DataSource sqliteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUrl(getUrl());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sqliteEntityManager(@Qualifier("sqliteDataSource") DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.vshmaliukh.spring_shelf_core.shelf.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager sqliteTransactionManager(@Qualifier("sqliteEntityManager") EntityManagerFactory entityManagerFactory) {
        return super.transactionManager(entityManagerFactory);
    }

}
