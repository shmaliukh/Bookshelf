package org.vshmaliukh.terminal.menus;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;

import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider.uniqueTypeNames;

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
