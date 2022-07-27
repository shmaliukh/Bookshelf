package org.vshmaliukh.terminal.menus;

public class MenuItemWithInfoAboutType<T> extends MenuItem{ // TODO rename

    final Class<T> classType;

    public MenuItemWithInfoAboutType(int index, String str, Class<T> classType) {
        super(index, str);
        this.classType = classType;
    }

    public Class<T> getClassType() {
        return classType;
    }
}
