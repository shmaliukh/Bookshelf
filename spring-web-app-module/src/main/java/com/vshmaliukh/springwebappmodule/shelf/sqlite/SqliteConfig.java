package com.vshmaliukh.springwebappmodule.shelf.sqlite;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.file.Paths;
import java.util.Properties;

import static org.vshmaliukh.services.file_service.UserFilesHandler.PROGRAM_DIR_NAME;

@Configuration
@Profile({"default"})
@EnableJpaRepositories(
        basePackages = "com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories",
        entityManagerFactoryRef = "sqliteEntityManager",
        transactionManagerRef = "sqliteTransactionManager")
public class SqliteConfig {

    public static final String SQLITE_DB_URL = "jdbc:sqlite://"+Paths.get(System.getProperty("user.home"), PROGRAM_DIR_NAME)+"/shelf_sqllite_db_2.db";

    @Bean
    public DataSource sqliteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC"
//                env.getProperty(JDBC_DRIVER_CLASS_NAME)
        );
        dataSource.setUrl(SQLITE_DB_URL
//                env.getProperty(JDBC_URL)
        );
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

    //server.port=${shelf.port}
    //spring.jpa.hibernate.ddl-auto=update
    //
    //spring.jpa.database-platform=com.vshmaliukh.springwebappmodule.shelf.sqlite.SQLDialect
    //spring.ds-sqlite.driverClassName=org.sqlite.JDBC
    //spring.ds-sqlite.jdbc-url = jdbc:sqlite:${shelf.home}/${shelf.sqliteDbFile}

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update"
//                env.getProperty("hibernate.hbm2ddl.auto")
        );
        hibernateProperties.setProperty("hibernate.dialect", "org.sqlite.hibernate.dialect.SQLiteDialect"
//                env.getProperty("hibernate.dialect")
        );
        hibernateProperties.setProperty("hibernate.show_sql", "true"
//                env.getProperty("hibernate.show_sql")
        );
        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true"
//                env.getProperty("hibernate.globally_quoted_identifiers")
        );
        return hibernateProperties;
    }
}
