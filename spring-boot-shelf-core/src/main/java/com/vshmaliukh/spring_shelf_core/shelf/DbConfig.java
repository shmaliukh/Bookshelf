package com.vshmaliukh.spring_shelf_core.shelf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Getter
@Setter
public class DbConfig extends DataSourceProperties {

    private String hibernateDialect;
    private String hibernateHbm2ddlAuto;

    //private String hibernateShowSql;
    //private String hibernateGloballyQuotedIdentifiers;

    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    protected final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", getHibernateDialect());
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", getHibernateHbm2ddlAuto());
        return hibernateProperties;
    }

}
