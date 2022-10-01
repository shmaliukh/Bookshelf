package com.vshmaliukh.springwebappmodule.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.vshmaliukh.print_table_service.TableGenerator;
import org.vshmaliukh.services.ConvertorToStringForItems;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemTitles;

import java.util.*;

import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.Constants.*;
import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;

public final class ControllerUtils {

    private ControllerUtils() {
    }

    public static String getFriendlyTypeOfWorkStr(int currentTypeOfWork) {
        Integer currentTypeOfWorkInteger = currentTypeOfWork;
        return typeOfWorkMap.get(currentTypeOfWorkInteger);
    }

    public static void formCurrentStateTable(String userName, int typeOfWork, ModelMap modelMap) {
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userName, typeOfWork);
        List<String> titles = new ArrayList<>();
        List<List<String>> tableValues = new ArrayList<>();
        if (webShelfHandler != null) {
            Shelf shelf = webShelfHandler.getShelf();
            if (shelf != null) {
                List<Item> allItems = shelf.getAllLiteratureObjects();
                List<Map<String, String>> table = ConvertorToStringForItems.getTable(allItems);
                TableGenerator tableGenerator = new TableGenerator(table, ItemTitles.TITLE_LIST, true);
                titles.addAll(tableGenerator.getGeneratedTitleList());
                tableValues.addAll(tableGenerator.getGeneratedTableList());
            }
        }
        modelMap.addAttribute(TITLES, titles);
        modelMap.addAttribute(ITEMS, tableValues);
    }

    public static void formItemTableByClass(String userName, int typeOfWork, String itemClassType, String menuItemIndex, ModelMap modelMap) {
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userName, typeOfWork);
        List<String> titles = new ArrayList<>();
        List<List<String>> tableValues = new ArrayList<>();
        if (webShelfHandler != null) {
            Shelf shelf = webShelfHandler.getShelf();
            if (shelf != null) {
                Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
                List<? extends Item> itemsByClass = webShelfHandler.getSortedItemsByClass(classByName);

                if (StringUtils.isNotBlank(menuItemIndex)) {
                    int typeOfSorting = Integer.parseInt(menuItemIndex);
                    ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classByName);
                    itemsByClass = handlerByClass.getSortedItems(typeOfSorting, itemsByClass);
                }
                List<Map<String, String>> table = ConvertorToStringForItems.getTable(itemsByClass);
                TableGenerator tableGenerator = new TableGenerator(table, ItemTitles.TITLE_LIST, true);
                titles.addAll(tableGenerator.getGeneratedTitleList());
                tableValues.addAll(tableGenerator.getGeneratedTableList());
            }
        }
        modelMap.addAttribute(TITLES, titles);
        modelMap.addAttribute(ITEMS, tableValues);
    }

    public static void formRadioButtonsMapForSortingByClassType(String itemClassType, ModelMap modelMap) {
        ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        List<MenuItem> sortingMenuList = handlerByName.getSortingMenuList();
        formRadioButtons(sortingMenuList, modelMap);
    }

    public static void formRadioButtons(List<? extends MenuItem> menuList, ModelMap modelMap) {
        Map<String, String> radioButtonsMap = new HashMap<>();
        for (MenuItem menuItem : menuList) {
            radioButtonsMap.put(String.valueOf(menuItem.getIndex()), menuItem.getStr());
        }
        modelMap.addAttribute(RADIO_BUTTONS, radioButtonsMap);
    }

    public static List<MenuItem> generateTypeOfWorkMenu(){
        ArrayList<MenuItem> menuList = new ArrayList<>();
        typeOfWorkMap.forEach((k,v) -> menuList.add(new MenuItem(k,v)));
        return Collections.unmodifiableList(menuList);
    }

    public static void formRadioButtonsToChooseTypeOfWork(String typeOfWork, ModelMap modelMap) {
        List<MenuItem> menuItems = generateTypeOfWorkMenu();
        formRadioButtons(menuItems, modelMap);
        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
    }

    public static String addFormIsRandomConfigStr(String itemClassType, String isRandom) {
        if (isRandom.equals("true")) {
            return itemClassType + "Rand";
        }
        return itemClassType;
    }
}
