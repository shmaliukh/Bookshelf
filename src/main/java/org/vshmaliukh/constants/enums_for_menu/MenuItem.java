package org.vshmaliukh.constants.enums_for_menu;

import lombok.Data;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;

import java.util.Comparator;

@Data
public class MenuItem {

    final int index;
    final String str;
    final Class<? extends Item> classType;
    final Comparator comparator;

    public MenuItem(int index, String str){
        this.index = index;
        this.str = str;
        classType = null;
        comparator = null;
    }

    public MenuItem(int index, String str, Class<? extends Item> classType){
        this.index = index;
        this.str = str;
        this.classType = classType;
        this.comparator = null;
    }

    public MenuItem(int index, String str, Comparator<?> comparator){
        this.index = index;
        this.str = str;
        this.classType = null;
        this.comparator = comparator;
    }

    @Override
    public String toString() {
        return index + " - " + str;
    }
}
