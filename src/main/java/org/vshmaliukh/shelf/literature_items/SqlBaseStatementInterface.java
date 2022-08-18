package org.vshmaliukh.shelf.literature_items;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.PUBLISHER;

public interface SqlBaseStatementInterface<T> {

    String ITEM_ID_SQL_PARAMETER = "id";
    String NAME_SQL_PARAMETER = NAME.toLowerCase();
    String PAGES_SQL_PARAMETER = PAGES.toLowerCase();
    String BORROWED_SQL_PARAMETER = BORROWED.toLowerCase();
    String AUTHOR_SQL_PARAMETER = AUTHOR.toLowerCase();
    String DATE_SQL_PARAMETER = DATE.toLowerCase();
    String PUBLISHER_SQL_PARAMETER = PUBLISHER.toLowerCase();

    String getSqlTableTitle(); // TODO rename

    default String deleteItemFromDBStr() {
        return "" +
                " DELETE FROM " +
                getSqlTableTitle() +
                " WHERE " + ITEM_ID_SQL_PARAMETER + " = ? ";
    }

    default String changeItemBorrowedStateInDBStr() {
        return "" +
                " UPDATE " +
                getSqlTableTitle() +
                " SET " + BORROWED + " = ? " +
                " WHERE " + ITEM_ID_SQL_PARAMETER + " = ? ";
    }

    void insertItemValues(PreparedStatement pstmt, T item, Integer userID) throws SQLException;

    T readItemFromSql(ResultSet rs) throws SQLException;

    String selectItemSqlStr();
}
