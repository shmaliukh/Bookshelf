package com.vshmaliukh.springwebappmodule.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.print_table_service.TableGenerator;
import org.vshmaliukh.services.ConvertorToStringForItems;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemTitles;

import java.util.*;

import static org.vshmaliukh.BootstrapHtmlBuilder.radioButton;
import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.Constants.*;
import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;
import static org.vshmaliukh.utils.WebUtils.generateTableOfShelfItems;

public class ControllerUtils {

    public static Map<String, String> adaptUserAtrToWebAppStandard(String userName, int typeOfWork) {
        Map<String, String> userAtrMap = new HashMap<>();
        userAtrMap.put(USER_NAME, userName);
        userAtrMap.put(TYPE_OF_WORK_WITH_FILES, String.valueOf(typeOfWork));
        return userAtrMap;
    }

    public static String generateTypeOfWorkRadioButtons(String currentTypeOfWork) {
        StringBuilder sb = new StringBuilder();
        Map<Object, String> typeOfWorkMap = ConfigFile.typeOfWorkMap;
        for (Map.Entry<Object, String> entry : typeOfWorkMap.entrySet()) {
            String integerTypeOfWork = entry.getKey().toString();
            String friendlyStrTypeOfWork = entry.getValue();
            if (currentTypeOfWork.equals(integerTypeOfWork)) {
                sb.append(radioButton(friendlyStrTypeOfWork, integerTypeOfWork, TYPE_OF_WORK_WITH_FILES, true));
            } else {
                sb.append(radioButton(friendlyStrTypeOfWork, integerTypeOfWork, TYPE_OF_WORK_WITH_FILES, false));
            }
        }
        return sb.toString();
    }

    public static String getFriendlyTypeOfWorkStr(String currentTypeOfWork) {
        return typeOfWorkMap.get(currentTypeOfWork);
    }

    public static String getFriendlyTypeOfWorkStr(int currentTypeOfWork) {
        Integer currentTypeOfWorkInteger = currentTypeOfWork;
        return typeOfWorkMap.get(currentTypeOfWorkInteger);
    }

    public static String generateRadioButtonsMenuHtmlStr(List<MenuItemClassType> generatedMenu) {
        StringBuilder generatedMenuBuilder = new StringBuilder();
        for (MenuItem menuItem : generatedMenu) {
            generatedMenuBuilder.append(radioButton(menuItem.getStr(), String.valueOf(menuItem.getIndex()), MENU_ITEM_INDEX, false, String.valueOf(menuItem.getIndex())));
        }
        return generatedMenuBuilder.toString();
    }

    public static <T extends Item> String generateItemsTableStr(Map<String, String> userAtr, String menuIndexStr, String classTypeStr, ItemHandler<T> handlerByName) {
        Class classType = ItemHandlerProvider.getClassByName(classTypeStr);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        List<T> sortedItemsByClass = new ArrayList<>();
        if (webShelfHandler != null) {
            sortedItemsByClass = webShelfHandler.getSortedItemsByClass(classType);
        }
        if (menuIndexStr != null && !menuIndexStr.equals("")) {
            int typeOfSorting = Integer.parseInt(menuIndexStr);
            List<T> sortedList = handlerByName.getSortedItems(typeOfSorting, sortedItemsByClass);
            return generateTableOfShelfItems(sortedList, true);
        } else {
            return generateTableOfShelfItems(sortedItemsByClass, true);
        }
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
        System.out.println(titles);
        modelMap.addAttribute(TITLES, titles);
        modelMap.addAttribute(ITEMS, tableValues);
    }

    public static void formRadioButtonsToSortItemsByValue(String itemClassType, ModelMap modelMap) {
        Map<String, String> radioButtonsMap = new HashMap<>();
        ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        List<MenuItemForSorting> sortingMenuList = handlerByName.getSortingMenuList();
        for (MenuItemForSorting menuItemForSorting : sortingMenuList) {
            radioButtonsMap.put(String.valueOf(menuItemForSorting.getIndex()), menuItemForSorting.getStr());
        }
        modelMap.addAttribute("radioButtons", radioButtonsMap);
    }
}
