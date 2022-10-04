package com.vshmaliukh.springwebappmodule.shelf.mysql;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static org.vshmaliukh.services.file_service.sql_handler.MySqlHandler.*;

@Configuration
@Profile({"default"})
@EnableJpaRepositories(
        basePackages = "com.vshmaliukh.springwebappmodule.shelf.mysql.repositories",
        entityManagerFactoryRef = "mySqlEntityManager",
        transactionManagerRef = "mySqlTransactionManager")
public class MySqlConfig {

    private static final String MYSQL_USER_NAME;

    private static final String MYSQL_PASSWORD;
    private static final String MYSQL_DB_URL;

    static {
        String mysqlUserName = System.getenv(MYSQL_USER_NAME_ENV);
        if (mysqlUserName == null || mysqlUserName.length() == 0) {
            mysqlUserName = "test";
        }
        MYSQL_USER_NAME = mysqlUserName;

        String mysqlPassword = System.getenv(MYSQL_PASSWORD_ENV);
        if (mysqlPassword == null || mysqlPassword.length() == 0) {
            mysqlPassword = "test";
        }
        MYSQL_PASSWORD = mysqlPassword;

        String mysqlPort = System.getenv(MYSQL_PORT_ENV);
        if (mysqlPort == null || mysqlPort.length() == 0) {
            mysqlPort = "localhost:3307";
        }
        String mysqlDbName = System.getenv(MYSQL_DB_NAME_ENV);
        if (mysqlDbName == null || mysqlDbName.length() == 0) {
            mysqlDbName = "my_test";
        }
        MYSQL_DB_URL = "jdbc:mysql://" + mysqlPort + "/" + mysqlDbName;
    }

    public static final String JDBC_DRIVER_CLASS_NAME = "jdbc.driverClassName";
    public static final String JDBC_URL = "jdbc.url";
    public static final String JDBC_USER = "jdbc.user";
    public static final String JDBC_PASS = "jdbc.pass";

//    private final Environment env;//todo implement late
//
//    public MySqlConfig(Environment env) {
//        this.env = env;
//    }

    //spring.ds-mysql.driverClassName=com.mysql.cj.jdbc.Driver
    //spring.ds-mysql.jdbc-url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
    //spring.ds-mysql.username=test
    //spring.ds-mysql.password=test

    @Bean
    @Primary
    public DataSource mySqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"
//                env.getProperty(JDBC_DRIVER_CLASS_NAME)
        );
        dataSource.setUrl(MYSQL_DB_URL
//                env.getProperty(JDBC_URL)
        );
        dataSource.setUsername(MYSQL_USER_NAME
//                env.getProperty(JDBC_USER)
        );
        dataSource.setPassword(MYSQL_PASSWORD
//                env.getProperty(JDBC_PASS)
        );
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mySqlEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mySqlDataSource());
        em.setPackagesToScan("com.vshmaliukh.springwebappmodule.shelf.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    @Primary
    public JpaTransactionManager mySqlTransactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update"
//                env.getProperty("hibernate.hbm2ddl.auto")
        );
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"
//                env.getProperty("hibernate.dialect"
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
