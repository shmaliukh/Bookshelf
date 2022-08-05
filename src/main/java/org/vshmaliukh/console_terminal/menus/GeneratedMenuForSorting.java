package org.vshmaliukh.console_terminal.menus;

import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemClassType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.vshmaliukh.bookshelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenuForSorting extends GeneratedMenu {

    public GeneratedMenuForSorting() {
        initMenuItems();
    }

    @Override
    void initMenuItems() {
        List<MenuItemClassType> sortingMenuItems = new ArrayList<>();
        int index = 1;
        List<Class<? extends Item>> uniqueTypeNamesList = uniqueTypeNames.stream().sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
        for (Class<? extends Item> typeName : uniqueTypeNamesList) {
            sortingMenuItems.add(new MenuItemClassType<>(index++, "Sort " + typeName.getSimpleName() + " items by value...", typeName));
        }
        generatedMenu = Collections.unmodifiableList(sortingMenuItems);
    }
}
