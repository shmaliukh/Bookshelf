package org.vshmaliukh.services.file_service.sql_handler;

import lombok.Data;
import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlItemHandler extends SaveReadUserFilesHandler implements AbleToHandleUserTableSql {

    UserContainer user; // TODO is necessary to keep reference to the user

    protected AbstractSqlItemHandler(String homeDir, String userName) {
        super(homeDir, userName);
        this.user = new UserContainer(userName);
        setUpSettings();
    }

    protected abstract void setUpSettings();

    public abstract Connection getConnectionToDB();

    public abstract void createNewTable(String sql);

    public abstract void saveItemToDB(Item item);

    public abstract void generateTablesIfNotExists();

    protected abstract void logSqlHandler(Exception e);

    public List<Item> readItemList() {
        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            List<? extends Item> itemByClassList = readItemsByClass(classType);
            itemList.addAll(itemByClassList);
        }
        return itemList;
    }

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

    public void changeItemBorrowedStateInDB(Item item) {
        Connection connection = getConnectionToDB();
        if (connection != null) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String changeItemBorrowedStateInDB = handlerByClass.changeItemBorrowedStateSqlStr();
            try (PreparedStatement preparedStatement = connection.prepareStatement(changeItemBorrowedStateInDB)) {
                preparedStatement.setString(1, String.valueOf(!item.isBorrowed()));
                preparedStatement.setInt(2, item.getId());
                preparedStatement.execute();
            } catch (SQLException sqle) {
                logSqlHandler(sqle);
            }
        }
    }

    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        Connection connection = getConnectionToDB();
        List<T> itemByClassList = new ArrayList<>();
        if (connection != null) {
            ItemHandler<T> handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String sqlStr = handlerByClass.selectItemSqlStr();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlStr)) {
                preparedStatement.setInt(1, user.getId());
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
}

@Data
class UserContainer {

    Integer id = null;
    final String name;

    public UserContainer(String name) {
        this.name = name;
    }

}
