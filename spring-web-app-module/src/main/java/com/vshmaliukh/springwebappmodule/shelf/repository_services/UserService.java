package com.vshmaliukh.springwebappmodule.shelf.repository_services;

public interface UserService {

    void insertUser(String userName);

    Integer readUserIdByName(String userName);

}
