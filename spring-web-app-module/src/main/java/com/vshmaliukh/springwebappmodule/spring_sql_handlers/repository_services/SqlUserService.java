package com.vshmaliukh.springwebappmodule.spring_sql_handlers.repository_services;

public interface SqlUserService {

    void insertUser(String userName);

    Integer readUserIdByName(String userName);

}
