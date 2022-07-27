package org.vshmaliukh.terminal.menus;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_RETURN;

class GeneratedMenuTest {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(baos, true);

    @ParameterizedTest
    @MethodSource("providedArgsForPrintingMenu")
    void testPrintingMenu(GeneratedMenu menuForSorting) {
        menuForSorting.printMenu(printWriter);
        String output = baos.toString().trim();

        assertFalse(output.isEmpty());
        assertTrue(output.startsWith(MESSAGE_TO_ENTER));
        assertTrue(output.contains(" - "));
        assertTrue(output.endsWith(MESSAGE_TO_RETURN));
    }

    private static Stream<Arguments> providedArgsForPrintingMenu() {
        return Stream.of(
                Arguments.of(new GeneratedMenuForSorting()),
                Arguments.of(new GeneratedMenuForAdding())
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsForInitMenuItems")
    void testInitMenuItems_menuForSorting(GeneratedMenu menuForSorting, int coef) {
        Set<Class> classSet = menuForSorting.generatedMenu.stream()
                .map(MenuItemClassType::getClassType)
                .collect(Collectors.toSet());

        assertFalse(menuForSorting.generatedMenu.isEmpty());
        Set<Class<? extends Item>> uniqueTypeNames = ItemHandlerProvider.uniqueTypeNames;
        assertEquals(uniqueTypeNames, classSet);
        assertEquals(uniqueTypeNames.size() * coef, menuForSorting.getMenuItems().size());
    }

    private static Stream<Arguments> providedArgsForInitMenuItems() {
        return Stream.of(
                Arguments.of(new GeneratedMenuForSorting(), 1),
                Arguments.of(new GeneratedMenuForAdding(), 2)
        );
    }
}