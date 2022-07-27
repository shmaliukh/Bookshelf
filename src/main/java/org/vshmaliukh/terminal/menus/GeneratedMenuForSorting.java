package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemWithInfoAboutType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenuForSorting extends GeneratedMenu {

    public GeneratedMenuForSorting() {
        initMenuItems();
    }

    @Override
    void initMenuItems() {
        List<MenuItemWithInfoAboutType> sortingMenuItems = new ArrayList<>();
        int index = 1;
        for (Class<? extends Item> typeName : uniqueTypeNames) {
            sortingMenuItems.add(new MenuItemWithInfoAboutType<>(index++, "Sort " + typeName.getSimpleName() + " items by value...", typeName));
        }
        generatedMenu = Collections.unmodifiableList(sortingMenuItems);
    }
}
