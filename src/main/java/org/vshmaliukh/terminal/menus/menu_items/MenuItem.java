package org.vshmaliukh.terminal.menus.menu_items;

import lombok.Data;

@Data
public class MenuItem {

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
