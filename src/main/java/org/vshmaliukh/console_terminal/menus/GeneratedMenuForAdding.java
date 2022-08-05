package org.vshmaliukh.console_terminal.menus;

import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemClassType;

import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.bookshelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

public class GeneratedMenuForAdding extends GeneratedMenu {

    public GeneratedMenuForAdding() {
        initMenuItems();
    }

    @Override
    void initMenuItems() {
        List<MenuItemClassType> addingMenuItems = new ArrayList<>();
        int index = 1;
        List<Class<? extends Item>> uniqueTypeNamesList = uniqueTypeNames.stream().sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
        for (Class<? extends Item> typeName : uniqueTypeNamesList) {
            addingMenuItems.add(new MenuItemClassType<>(index++, "Add new " + typeName.getSimpleName() + " item to Shelf", typeName));
            addingMenuItems.add(new MenuItemClassType<>(index++, "Add random " + typeName.getSimpleName() + " item to Shelf", typeName));
        }
        generatedMenu = Collections.unmodifiableList(addingMenuItems);
    }
}
