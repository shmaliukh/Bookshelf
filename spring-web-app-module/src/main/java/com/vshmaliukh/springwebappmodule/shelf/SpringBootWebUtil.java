package com.vshmaliukh.springwebappmodule.shelf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.vshmaliukh.print_table_service.TableHandler;
import org.vshmaliukh.services.ConvertorToStringForItems;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.Shelf;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.Constants.ITEMS;
import static org.vshmaliukh.Constants.TITLES;

@Service
public class SpringBootWebUtil {

    SpringBootUI springBootUI;

    public SpringBootWebUtil(SpringBootUI springBootUI) {
        this.springBootUI = springBootUI;
    }

    public SaveReadShelfHandler generateSpringBootShelfHandler(String userName, int typeOfWorkWithFiles) {
        if (StringUtils.isNotBlank(userName)) {
            SpringBootUI webUI = springBootUI;
            webUI.setUser(new User(userName));
            webUI.setTypeOfWorkWithFiles(typeOfWorkWithFiles);
            webUI.configShelfHandler();

            SaveReadShelfHandler shelfHandler = webUI.getShelfHandler();
            shelfHandler.readShelfItems();
            return shelfHandler;
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
        }
        modelMap.addAttribute(TITLES, titles);
        modelMap.addAttribute(ITEMS, tableValues);
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
            }
        }
        modelMap.addAttribute(TITLES, titles);
        modelMap.addAttribute(ITEMS, tableValues);
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

}
