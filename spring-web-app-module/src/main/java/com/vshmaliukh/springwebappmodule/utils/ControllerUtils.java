package com.vshmaliukh.springwebappmodule.utils;

import org.springframework.ui.ModelMap;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.util.*;

import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.Constants.RADIO_BUTTONS;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;

public final class ControllerUtils {

    public static String getFriendlyTypeOfWorkStr(int currentTypeOfWork) {
        Integer currentTypeOfWorkInteger = currentTypeOfWork;
        return typeOfWorkMap.get(currentTypeOfWorkInteger);
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

    public static List<MenuItem> generateTypeOfWorkMenu() {
        ArrayList<MenuItem> menuList = new ArrayList<>();
        typeOfWorkMap.forEach((k, v) -> menuList.add(new MenuItem(k, v)));
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
