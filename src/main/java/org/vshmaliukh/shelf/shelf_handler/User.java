package org.vshmaliukh.shelf.shelf_handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    protected Integer id = null; // todo is need to have 'id' param

    private final String name;

    public User(String name) {
        this.name = name;
    }

}
