package com.vshmaliukh.spring_shelf_core.shelf;

import org.vshmaliukh.MyLogUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Getter
@Setter
public class DataBaseConfig extends DataSourceProperties {

    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String HIBERNATE_HBM_2_DDL_AUTO = "hibernate.hbm2ddl.auto";

    private String hibernateDialect;
    private String hibernateHbm2ddlAuto;

    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    protected final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        String dialect = getHibernateDialect();
        hibernateProperties.setProperty(HIBERNATE_DIALECT, dialect);
        MyLogUtil.logDebug(this,"addition property '" + HIBERNATE_DIALECT + "' value: '{}'", dialect);
        String hbm2ddlAuto = getHibernateHbm2ddlAuto();
        hibernateProperties.setProperty(HIBERNATE_HBM_2_DDL_AUTO, hbm2ddlAuto);
        MyLogUtil.logDebug(this,"addition property '" + HIBERNATE_HBM_2_DDL_AUTO + "' value: '{}'", hbm2ddlAuto );
        return hibernateProperties;
    }

}
