package org.vshmaliukh.shelf.shelf_handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class User {

    protected Integer id = null;

    private final String name;

    public User(String name) {
        this.name = name;
    }
}
