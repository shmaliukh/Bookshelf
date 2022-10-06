package com.vshmaliukh.springwebappmodule.shelf;

import org.vshmaliukh.shelf.shelf_handler.User;

import java.util.List;

public interface ActionsWithItem<T> {

    default void saveItemList(List<T> listToSave){
        listToSave.forEach(this::saveItemToDB);
    }

    void saveItemToDB(T item);

    void insertUser(String userName);

    boolean isUserExist(String userName);

    void createUser();

    void readUserId(User user);


    //
    //    @Override
    //    public void insertUser(String userName) {
    ////        if (!isUserExist(userName)) {
    ////            String sql = " INSERT INTO " + USER_TABLE_TITLE + " ( " + USER_NAME_SQL_PARAMETER + " ) " + " VALUES ( ? ) ";
    ////            try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
    ////                preparedStatement.setString(1, userName);
    ////                preparedStatement.executeUpdate();
    ////            } catch (SQLException sqle) {
    ////                logSqlHandler(sqle);
    ////            }
    ////        }
    //    }
    //
    //    @Override
    //    public boolean isUserExist(String userName) {
    ////        String sql = " SELECT COUNT(" + USER_NAME_SQL_PARAMETER + ")" +
    ////                " FROM " + USER_TABLE_TITLE +
    ////                " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
    ////        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
    ////            preparedStatement.setString(1, userName);
    ////
    ////            ResultSet rs = preparedStatement.executeQuery();
    ////            if (rs.next()) {
    ////                int anInt = rs.getInt(1);
    ////                if (anInt == 0) {
    ////                    return false;
    ////                }
    ////            }
    ////        } catch (SQLException sqle) {
    ////            logSqlHandler(sqle);
    ////        }
    //        return true;
    //
    //    }
    //
    //    @Override
    //    public void createUser() {
    ////        String sql = " CREATE TABLE IF NOT EXISTS " + USER_TABLE_TITLE + " (\n" +
    ////                USER_ID_SQL_PARAMETER + " INT AUTO_INCREMENT PRIMARY KEY , \n" +
    ////                USER_NAME_SQL_PARAMETER + " VARCHAR(255) NOT NULL , \n" +
    ////                " UNIQUE ( \n" +
    ////                USER_NAME_SQL_PARAMETER + " )\n" +
    ////                ");";
    ////        createNewTable(sql);
    ////        insertUser(user.getName());
    ////        readUserId(user);
    //    }
    //
    //    @Override
    //    public void readUserId(UserContainer user) {
    ////        String sql = "" +
    ////                " SELECT " + USER_ID_SQL_PARAMETER +
    ////                " FROM " + USER_TABLE_TITLE +
    ////                " WHERE " + USER_NAME_SQL_PARAMETER + " = ? ";
    ////        try (PreparedStatement preparedStatement = getConnectionToDB().prepareStatement(sql)) {
    ////            preparedStatement.setString(1, user.getName());
    ////
    ////            ResultSet rs = preparedStatement.executeQuery();
    ////            if (rs.next()) {
    ////                user.setId(rs.getInt(USER_ID_SQL_PARAMETER));
    ////            }
    ////        } catch (SQLException sqle) {
    ////            logSqlHandler(sqle);
    ////        }
    //    }
}
