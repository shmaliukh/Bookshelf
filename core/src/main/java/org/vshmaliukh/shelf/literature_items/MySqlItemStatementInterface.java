package org.vshmaliukh.shelf.literature_items;

public interface MySqlItemStatementInterface {

    String INT_AUTO_INCREMENT = " INT AUTO_INCREMENT ";
    String INT_NOT_NULL = " INT NOT NULL ";
    String VARCHAR_255_NOT_NULL = " VARCHAR(255) NOT NULL ";
    String DATE_NOT_NULL = " DATE NOT NULL ";
    String BIT_NOT_NULL = " BIT NOT NULL ";
    String PRIMARY_KEY = " PRIMARY KEY ( ";
    String CONSTRAINT_UC = " CONSTRAINT UC_";

    String insertItemMySqlStr();

    String createTableMySqlStr();

}
