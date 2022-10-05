package com.vshmaliukh.springwebappmodule.shelf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Getter
@Setter
public class DbConfig extends DataSourceProperties {

    private String hibernateDialect;

    //private String hibernateHbm2ddlAuto;
    //private String hibernateShowSql;
    //private String hibernateGloballyQuotedIdentifiers;

    @Bean
    @Primary
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    protected final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", getHibernateDialect());
        return hibernateProperties;
    }

}
