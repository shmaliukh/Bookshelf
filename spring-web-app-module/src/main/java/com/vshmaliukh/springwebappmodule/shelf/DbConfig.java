package com.vshmaliukh.springwebappmodule.shelf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@Getter
@Setter
public class DbConfig extends DataSourceProperties {

    private String hibernateDialect;

    //private String hibernateHbm2ddlAuto;
    //private String hibernateShowSql;
    //private String hibernateGloballyQuotedIdentifiers;

}
