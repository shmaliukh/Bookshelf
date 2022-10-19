package org.vshmaliukh.services.data_service.sql_handler;

import lombok.Data;

@Data
public class UserContainer {

    Integer id = null;
    final String name;

    public UserContainer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
