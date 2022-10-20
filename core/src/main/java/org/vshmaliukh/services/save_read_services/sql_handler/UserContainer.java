package org.vshmaliukh.services.save_read_services.sql_handler;

import lombok.Data;

@Data
public class UserContainer {

    private Integer id = null;
    private final String name;

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
