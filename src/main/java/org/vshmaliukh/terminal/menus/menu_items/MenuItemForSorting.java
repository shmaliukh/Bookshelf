package org.vshmaliukh.terminal.menus.menu_items;

import java.util.Comparator;

public class MenuItemForSorting<T> extends MenuItem {

    final Comparator<T> comparator;

    public MenuItemForSorting(int index, String str, Comparator<T> comparator) {
        super(index, str);
        this.comparator = comparator;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }
}
