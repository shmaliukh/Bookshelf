package org.vshmaliukh.terminal.menus;

import lombok.Data;

@Data
public abstract class MenuItem {

    final int index;
    final String str;

    public MenuItem(int index, String str){
        this.index = index;
        this.str = str;
    }

    @Override
    public String toString() {
        return index + " - " + str;
    }
}
