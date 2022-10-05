package com.vshmaliukh.springwebappmodule.shelf.sqlite;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.vshmaliukh.services.file_service.UserFilesHandler.PROGRAM_DIR_NAME;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories",
        entityManagerFactoryRef = "sqliteEntityManager",
        transactionManagerRef = "sqliteTransactionManager")
public class SqliteHandlerConfigurator {


    public static final Path SQLITE_HOME = Paths.get(System.getProperty("user.home"), PROGRAM_DIR_NAME);
    public static final String SQLITE_FILE_NAME = "shelf_sqlite_db.db";
    public static final String SQLITE_DB_URL = "jdbc:sqlite://" + SQLITE_HOME + "/" + SQLITE_FILE_NAME;

    private final SqliteYAMLConfig config;

    public SqliteHandlerConfigurator(SqliteYAMLConfig config) {
        this.config = config;
    }


    @Bean
    public DataSource sqliteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUrl(SQLITE_DB_URL);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sqliteEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(sqliteDataSource());
        em.setPackagesToScan("com.vshmaliukh.springwebappmodule.shelf.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager sqliteTransactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", config.getHibernateDialect());//"org.sqlite.hibernate.dialect.SQLiteDialect"
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", config.getSqliteConfig().getHibernateHbm2ddlAuto()); //true
//        hibernateProperties.setProperty("hibernate.show_sql", config.getSqliteConfig().getHibernateShowSql());//"true"
//        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", config.getSqliteConfig().getHibernateGloballyQuotedIdentifiers());//"true"
        return hibernateProperties;
    }
}
