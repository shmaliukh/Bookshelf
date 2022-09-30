package org.vshmaliukh.literature_items;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.services.input_handler.ConstantsForConsoleUserInputHandler;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.shelf.AbstractUI.*;

@Slf4j
class ItemHandlerTest {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(baos, true);

    Random random = new Random();

    Set<Class<? extends Item>> uniqueTypeNames = ItemHandlerProvider.uniqueTypeNames;

    @Test
    void testGetSortedItems() {
        List<? extends Item> itemList = generateListWithThreeItemsPerType();

        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            List<MenuItemForSorting> sortingMenuList = handlerByClass.getSortingMenuList();
            for (MenuItemForSorting menuItem : sortingMenuList) {
                List<? extends Item> itemsByType = ItemUtils.getItemsByType(uniqueTypeName, itemList);
                List sortedItems = handlerByClass.getSortedItems(menuItem.getIndex(), itemsByType);

                log.info("uniqueTypeName = '{}' // type of sorting = '{}' //  items = '{}' ",
                        uniqueTypeName.getSimpleName(), menuItem.getStr(), sortedItems.toString());

                assertFalse(sortedItems.isEmpty());
                assertEquals(itemList.size() / uniqueTypeNames.size(), sortedItems.size());
                sortedItems.forEach(o -> assertEquals(uniqueTypeName, o.getClass()));
            }
        }
    }

    private List<? extends Item> generateListWithThreeItemsPerType() {
        List itemList = new ArrayList<>();
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            itemList.add(handlerByClass.getRandomItem(random));
            itemList.add(handlerByClass.getRandomItem(random));
            itemList.add(handlerByClass.getRandomItem(random));
        }
        return itemList;
    }

    @Test
    void testGetSortingMenuList() {
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            List<MenuItemForSorting> sortingMenuList = handlerByClass.getSortingMenuList();
            for (int i = 0; i < sortingMenuList.size(); i++) {
                MenuItemForSorting menuItem = sortingMenuList.get(i);

                log.info("uniqueTypeName = '{}' // menuItem = '{}' // sortingMenuList = '{}' ",
                        uniqueTypeName.getSimpleName(), menuItem, sortingMenuList);

                Assertions.assertEquals(i + 1, menuItem.getIndex());
                Assertions.assertFalse(menuItem.getStr().isEmpty());
                Assertions.assertFalse(menuItem.getComparator().toString().isEmpty());
                Assertions.assertFalse(menuItem.getStr().isEmpty());
            }
        }
    }

    @Test
    void testPrintSortingMenu() {
        String output;
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            handlerByClass.printSortingMenu(printWriter);
            System.out.println(baos);

            output = baos.toString().trim();
            assertFalse(output.isEmpty());
            assertTrue(output.startsWith(CHOOSE_TYPE_OF_SORTING));
            assertTrue(output.contains(" - "));
            assertTrue(output.endsWith(ENTER_ANOTHER_VALUE_TO_RETURN));
        }
    }

    @Test
    void testClarificationForSortingItems_emptyList() {
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            handlerByClass.clarificationForSortingItems(new ArrayList<>(), 0, printWriter);
            System.out.println(baos);

            String output = baos.toString().trim();
            Assertions.assertEquals(NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING, output);
            baos.reset();
        }
    }

    @Test
    void testClarificationForSortingItems() {
        String output;
        List<? extends Item> generatedListWithAllTypeItems = generateListWithThreeItemsPerType();

        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            List<? extends Item> itemsByType = ItemUtils.getItemsByType(uniqueTypeName, generatedListWithAllTypeItems);

            List<MenuItemForSorting> sortingMenuList = handlerByClass.getSortingMenuList();
            for (MenuItemForSorting menuItem : sortingMenuList) {
                log.info("uniqueTypeName = '{}' // menuIndex = '{}' // menuItem = '{}' // itemsByType = '{}'",
                        uniqueTypeName.getSimpleName(), menuItem.getIndex(), menuItem, itemsByType);


                handlerByClass.clarificationForSortingItems(itemsByType, menuItem.getIndex(), printWriter);

                output = baos.toString().trim();

                assertFalse(output.isEmpty());
                assertTrue(output.startsWith(CHOOSE_TYPE_OF_SORTING));
                assertTrue(output.contains(" - "));
                assertTrue(output.endsWith(ENTER_ANOTHER_VALUE_TO_RETURN));
            }

        }
    }

    @Test
    void testGetItemByUserInput() {
        Scanner scanner = new Scanner(System.lineSeparator());
        ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature = new ConsoleInputHandlerForLiterature(scanner, printWriter);
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            Item itemByUserInput = handlerByClass.getItemByUserInput(consoleInputHandlerForLiterature, printWriter);

            assertNotNull(itemByUserInput);
            assertNotNull(itemByUserInput.toString());
            assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_STRING, itemByUserInput.getName());
            assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_INT, itemByUserInput.getPagesNumber());
        }
    }

    @Test
    void testGetRandomItem() {
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            Item itemByUserInput = handlerByClass.getRandomItem(random);

            assertNotNull(itemByUserInput);
            assertNotNull(itemByUserInput.toString());
            assertNotNull(itemByUserInput.getName());
            assertTrue(itemByUserInput.getPagesNumber() > 0);
        }
    }

    @Test
    void testConvertItemToListOfString() {
        List<? extends Item> generatedListWithAllTypeItems = generateListWithThreeItemsPerType();
        for (Class<? extends Item> uniqueTypeName : uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(uniqueTypeName);
            List<? extends Item> itemsByType = ItemUtils.getItemsByType(uniqueTypeName, generatedListWithAllTypeItems);
            for (Item item : itemsByType) {
                Map map = handlerByClass.convertItemToMapOfString(item);
                assertFalse(map.isEmpty());
                assertFalse(map.values().isEmpty());
                assertFalse(map.keySet().isEmpty());
                assertFalse(map.keySet().isEmpty());
                // TODO do better validation
            }
        }
    }
}