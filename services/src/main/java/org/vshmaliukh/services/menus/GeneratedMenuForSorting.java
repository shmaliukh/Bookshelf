package org.vshmaliukh.services.menus;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.vshmaliukh.shelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenuForSorting extends GeneratedMenu {

    public GeneratedMenuForSorting() {
        initMenuItems();
    }

    @Override
    void initMenuItems() {
        List<MenuItemClassType> sortingMenuItems = new ArrayList<>();
        int index = 1;
        List<Class<? extends Item>> uniqueTypeNamesList = ItemHandlerProvider.uniqueTypeNames.stream().sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
        for (Class<? extends Item> typeName : uniqueTypeNamesList) {
            sortingMenuItems.add(new MenuItemClassType<>(index++, "Sort " + typeName.getSimpleName() + " items by value...", typeName));
        }
        generatedMenu = Collections.unmodifiableList(sortingMenuItems);
    }
}
