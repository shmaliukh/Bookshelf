package org.vshmaliukh.constants.enums_for_menu;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;

public class MenuItem {

    final int index;
    final String str;
    final Class<? extends Item> classType;

    public MenuItem(int index, String str){
        this.index = index;
        this.str = str;
        classType = null;
    }

    public MenuItem(int index, String str, Class<? extends Item> classType){
        this.index = index;
        this.str = str;
        this.classType = classType;
    }

    public Class getClassType(){
        return classType;
    }

    @Override
    public String toString() {
        return index + " - " + str;
    }
}
