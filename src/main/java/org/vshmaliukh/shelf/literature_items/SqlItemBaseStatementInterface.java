package org.vshmaliukh.shelf.literature_items;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.PUBLISHER;

public interface SqlItemBaseStatementInterface<T> {

    String ITEM_ID_SQL_PARAMETER = "id";
    String NAME_SQL_PARAMETER = NAME.toLowerCase();
    String PAGES_SQL_PARAMETER = PAGES.toLowerCase();
    String BORROWED_SQL_PARAMETER = BORROWED.toLowerCase();
    String AUTHOR_SQL_PARAMETER = AUTHOR.toLowerCase();
    String DATE_SQL_PARAMETER = DATE.toLowerCase();
    String PUBLISHER_SQL_PARAMETER = PUBLISHER.toLowerCase();

    String CREATE_TABLE_IF_NOT_EXISTS = " CREATE TABLE IF NOT EXISTS ";
    String DELETE_FROM = " DELETE FROM ";
    String WHERE = " WHERE ";
    String UPDATE = " UPDATE ";
    String SET = " SET ";
    String UNIQUE = " UNIQUE ";

    String sqlItemTableTitle();

    default String deleteItemSqlStr() {
        return "" +
                DELETE_FROM +
                sqlItemTableTitle() +
                WHERE + ITEM_ID_SQL_PARAMETER + " = ? ";
    }

    default String changeItemBorrowedStateSqlStr() {
        return "" +
                UPDATE +
                sqlItemTableTitle() +
                SET + BORROWED + " = ? " +
                WHERE + ITEM_ID_SQL_PARAMETER + " = ? ";
    }

    void insertItemValuesToSqlDB(PreparedStatement preparedStatement, T item, Integer userID) throws SQLException;

    T readItemFromSqlDB(ResultSet rs) throws SQLException;

    String selectItemSqlStr();
}
