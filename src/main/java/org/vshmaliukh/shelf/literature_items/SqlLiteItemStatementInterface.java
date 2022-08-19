package org.vshmaliukh.shelf.literature_items;

public interface SqlLiteItemStatementInterface {

    String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT ";
    String INTEGER_NOT_NULL = " INTEGER NOT NULL ";
    String TEXT_NOT_NULL = " TEXT NOT NULL ";
    String ON_CONFLICT_IGNORE = " ON CONFLICT IGNORE ";

    String insertItemSqlLiteStr();

    String createTableSqlLiteStr();

}
