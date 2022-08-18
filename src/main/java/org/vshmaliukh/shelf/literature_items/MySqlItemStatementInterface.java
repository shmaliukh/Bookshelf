package org.vshmaliukh.shelf.literature_items;

public interface MySqlItemStatementInterface {

    String INT_AUTO_INCREMENT = " INT AUTO_INCREMENT ";
    String INT_NOT_NULL = " INT NOT NULL ";
    String VARCHAR_200_NOT_NULL = " VARCHAR(200) NOT NULL ";
    String VARCHAR_25_NOT_NULL = " VARCHAR(25) NOT NULL ";
    String VARCHAR_10_NOT_NULL = " VARCHAR(10) NOT NULL ";
    String PRIMARY_KEY = " PRIMARY KEY ( ";
    String CONSTRAINT_UC = " CONSTRAINT UC_";
    String UNIQUE = " UNIQUE ";

    String insertItemMySqlStr();

    String generateMySqlTableStr();

}
