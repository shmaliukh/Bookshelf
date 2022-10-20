package com.vshmaliukh.spring_shelf_core.shelf.repository_services;

public interface SqlUserService {

    void insertUser(String userName);

    Integer readUserIdByName(String userName);

}
