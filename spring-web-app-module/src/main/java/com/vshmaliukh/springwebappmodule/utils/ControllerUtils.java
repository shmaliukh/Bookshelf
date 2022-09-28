package com.vshmaliukh.springwebappmodule.utils;

import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if(currentTypeOfWork.equals(integerTypeOfWork)){
                sb.append(radioButton(friendlyStrTypeOfWork, integerTypeOfWork, TYPE_OF_WORK_WITH_FILES, true));
            }
            else {
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

    public static  <T extends Item> String generateItemsTableStr(Map<String, String> userAtr, String menuIndexStr, String classTypeStr, ItemHandler<T> handlerByName) {
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
}
