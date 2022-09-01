package org.vshmaliukh.services.menus;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;

import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.shelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenuForAdding extends GeneratedMenu {

    public GeneratedMenuForAdding() {
        initMenuItems();
    }

    @Override
    void initMenuItems() {
        List<MenuItemClassType> addingMenuItems = new ArrayList<>();
        int index = 1;
        List<Class<? extends Item>> uniqueTypeNamesList = ItemHandlerProvider.uniqueTypeNames.stream().sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
        for (Class<? extends Item> typeName : uniqueTypeNamesList) {
            addingMenuItems.add(new MenuItemClassType<>(index++, "Add new " + typeName.getSimpleName() + " item to Shelf", typeName));
            addingMenuItems.add(new MenuItemClassType<>(index++, "Add random " + typeName.getSimpleName() + " item to Shelf", typeName));
        }
        generatedMenu = Collections.unmodifiableList(addingMenuItems);
    }
}
