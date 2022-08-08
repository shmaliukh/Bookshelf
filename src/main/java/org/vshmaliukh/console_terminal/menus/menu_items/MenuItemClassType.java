package org.vshmaliukh.console_terminal.menus.menu_items;

import org.vshmaliukh.bookshelf.literature_items.Item;

public class MenuItemClassType<T extends Item> extends MenuItem{ // TODO rename

    final Class<T> classType;

    public MenuItemClassType(int index, String str, Class<T> classType) {
        super(index, str);
        this.classType = classType;
    }

    public Class<T> getClassType() {
        return classType;
    }
}
