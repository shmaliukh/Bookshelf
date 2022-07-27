package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenuForAdding extends GeneratedMenu {

    public GeneratedMenuForAdding() {
        initMenuItems();
    }

    @Override
    void initMenuItems() {
        List<MenuItemClassType> addingMenuItems = new ArrayList<>();
        int index = 1;
        for (Class<? extends Item> typeName : uniqueTypeNames) {
            addingMenuItems.add(new MenuItemClassType<>(index++, "Add new " + typeName.getSimpleName() + " item to Shelf", typeName));
            addingMenuItems.add(new MenuItemClassType<>(index++, "Add random " + typeName.getSimpleName() + " item to Shelf", typeName));
        }
        generatedMenu = Collections.unmodifiableList(addingMenuItems);
    }
}
