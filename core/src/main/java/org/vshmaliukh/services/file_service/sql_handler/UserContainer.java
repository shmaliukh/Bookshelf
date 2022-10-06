package org.vshmaliukh.services.file_service.sql_handler;

import lombok.Data;

@Data
public class UserContainer {

    Integer id = null;
    final String name;

    public UserContainer(String name) {
        this.name = name;
    }

}
