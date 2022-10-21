package org.vshmaliukh.services.save_read_services.sql_handler;

import lombok.NoArgsConstructor;
import org.vshmaliukh.services.save_read_services.AbstractSaveReadService;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public abstract class AbstractSqlHandler extends AbstractSaveReadService implements SqlHandler {

    protected AbstractSqlHandler(String userName) {
        super(userName);
        this.userContainer = new UserContainer(userName);
        setUpSettings();
    }

    @Override
    public List<Item> readItemList() {
        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            List<? extends Item> itemByClassList = readItemsByClass(classType);
            itemList.addAll(itemByClassList);
        }
        return itemList;
    }

    @Override
    public void deleteItemFromDB(Item item) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String deleteItemFromDBStr = handlerByClass.deleteItemSqlStr();
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteItemFromDBStr)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.execute();
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String changeItemBorrowedStateInDB = handlerByClass.changeItemBorrowedStateSqlStr();
            try (PreparedStatement preparedStatement = connection.prepareStatement(changeItemBorrowedStateInDB)) {
                preparedStatement.setBoolean(1,!item.isBorrowed());
                preparedStatement.setInt(2, item.getId());
                preparedStatement.execute();
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        Connection connection = getConnectionToDB();
        List<T> itemByClassList = new ArrayList<>();
        if (connection != null) {
            ItemHandler<T> handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String sqlStr = handlerByClass.selectItemSqlStr();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlStr)) {
                preparedStatement.setInt(1, userContainer.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    T item = handlerByClass.readItemFromSqlDB(resultSet);
                    itemByClassList.add(item);
                }
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
        return itemByClassList;
    }

    @Override
    public void saveItemToDB(Item item) {
        ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
        String sqlInsertStr = handlerByClass.insertItemMySqlStr();
        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sqlInsertStr)) {
            handlerByClass.insertItemValuesToSqlDB(preparedStatement, item, userContainer.getId());
        } catch (SQLException sqle) {
            logSqlHandler(sqle);
        }
    }

    @Override
    public void saveItemListToDB(List<Item> listToSave) {
        listToSave.forEach(this::saveItemToDB);
    }
}

