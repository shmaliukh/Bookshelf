package org.vshmaliukh.constants.enums_for_menu;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    @Test
    void testPrintMainMenu() {
        Menu.printMainMenu(printWriter);
    }

    @Test
    void testPrintAddingMenu() {
        Menu.printAddingMenu(printWriter);
    }

    @Test
    void testPrintSortingMenu() {
        Menu.printSortingMenu(printWriter);
    }
}