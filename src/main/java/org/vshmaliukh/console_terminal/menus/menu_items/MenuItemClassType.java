package org.vshmaliukh.console_terminal.menus.menu_items;

public class MenuItemClassType<T> extends MenuItem{ // TODO rename

    final Class<T> classType;

    public MenuItemClassType(int index, String str, Class<T> classType) {
        super(index, str);
        this.classType = classType;
    }

    public Class<T> getClassType() {
        return classType;
    }
}
