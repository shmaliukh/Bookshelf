package com.vshmaliukh.springbootshelfcore.shelf.repository_services;

public interface SqlUserService {

    void insertUser(String userName);

    Integer readUserIdByName(String userName);

}
