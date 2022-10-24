package com.vshmaliukh.spring_web_app_module;

import com.vshmaliukh.spring_shelf_core.shelf.SpringBootUI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.vshmaliukh.Constants;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.print_table_service.TableHandler;
import org.vshmaliukh.services.ConvertorToStringForItems;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpringBootWebUtil {

    SpringBootUI springBootUI;

    public SpringBootWebUtil(SpringBootUI springBootUI) {
        this.springBootUI = springBootUI;
    }

    public SaveReadShelfHandler generateSpringBootShelfHandler(String userName, int saveReadServiceType) {
        if (StringUtils.isNotBlank(userName)) {
            SpringBootUI webUI = springBootUI;
            webUI.setUser(new User(userName));
            webUI.setSaveReadServiceType(saveReadServiceType);
            webUI.configShelfHandler();

            SaveReadShelfHandler shelfHandler = webUI.getShelfHandler();
            shelfHandler.readShelfItems();
            return shelfHandler;
        } else {
            MyLogUtil.logWarn(this, "userName: '{}' // type of work: '{}' " +
                    "// problem to generate SpringBootShelfHandler: userName is blank ", userName, saveReadServiceType);
            MyLogUtil.logDebug(this, "generateSpringBootShelfHandler(userName: '{}', saveReadServiceType: '{}') ", userName, saveReadServiceType);
        }
        return null;
    }

    public void formCurrentStateTable(String userName, int typeOfWork, ModelMap modelMap) {
        SaveReadShelfHandler shelfHandler = generateSpringBootShelfHandler(userName, typeOfWork);
        List<String> titles = new ArrayList<>();
        List<List<String>> tableValues = new ArrayList<>();
        Shelf shelf = shelfHandler.getShelf();
        if (shelf != null) {
            List<Item> allItems = shelf.getAllLiteratureObjects();
            List<Map<String, String>> table = ConvertorToStringForItems.getTable(allItems);
            TableHandler tableGenerator = new TableHandler(table, ItemTitles.TITLE_LIST, true);
            titles.addAll(tableGenerator.getGeneratedTitleList());
            tableValues.addAll(tableGenerator.getGeneratedTableList());
        } else {
            MyLogUtil.logWarn(this, "userName: '{}' // type of work: '{}' // problem to form current state table: shelf == null ", userName, typeOfWork);
            MyLogUtil.logDebug(this, "formCurrentStateTable(userName: '{}', typeOfWork: '{}', modelMap: '{}') " +
                    "// shelfHandler: '{}'", userName, typeOfWork, modelMap, shelfHandler);
        }
        modelMap.addAttribute(Constants.TITLES, titles);
        modelMap.addAttribute(Constants.ITEMS, tableValues);
    }

    public void formItemTableByClass(String userName, int typeOfWork, String itemClassType, String menuItemIndex, ModelMap modelMap) {
        SaveReadShelfHandler webShelfHandler = generateSpringBootShelfHandler(userName, typeOfWork);
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
                TableHandler tableGenerator = new TableHandler(table, ItemTitles.TITLE_LIST, true);
                titles.addAll(tableGenerator.getGeneratedTitleList());
                tableValues.addAll(tableGenerator.getGeneratedTableList());
            } else {
                MyLogUtil.logWarn(this, "userName: '{}' // type of work: '{}' // problem to form item table by class: shelf == null ", userName, typeOfWork);
                MyLogUtil.logDebug(this, "formItemTableByClass(userName: '{}', typeOfWork: '{}', itemClassType: '{}', menuItemIndex: '{}', modelMap: '{}') " +
                        "// webShelfHandler: '{}'", userName, typeOfWork, itemClassType, menuItemIndex, modelMap, webShelfHandler);
            }
        } else {
            MyLogUtil.logWarn(this, "userName: '{}' // type of work: '{}' // problem to form item table by class: webShelfHandler == null ", userName, typeOfWork);
            MyLogUtil.logDebug(this, "formItemTableByClass(userName: '{}', typeOfWork: '{}', itemClassType: '{}', menuItemIndex: '{}', modelMap: '{}') ",
                    userName, typeOfWork, itemClassType, menuItemIndex, modelMap);
        }
        modelMap.addAttribute(Constants.TITLES, titles);
        modelMap.addAttribute(Constants.ITEMS, tableValues);
    }

}
