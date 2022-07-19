package org.vshmaliukh.services.print_table_service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TablePrinterTest {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);

    PrintWriter printWriter = new PrintWriter(printStream, true);

    @Test
    void testOneItemToPrint_onlyTitle() {
        String expectedString =
                "" +
                        "┌─────────┐" + System.lineSeparator() +
                        "│ 1 title │" + System.lineSeparator() +
                        "│─────────│" + System.lineSeparator() +
                        "└─────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title")),
                new ArrayList<>(), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testTwoItemsToPrint_onlyTitle() {
        String expectedString =
                "" +
                        "┌─────────┬─────────┐" + System.lineSeparator() +
                        "│ 1 title │ 2 title │" + System.lineSeparator() +
                        "│─────────┼─────────│" + System.lineSeparator() +
                        "└─────────┴─────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title", "2 title")),
                new ArrayList<>(), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testThreeItemsToPrint_onlyTitle() {
        String expectedString =
                "" +
                        "┌─────────┬─────────┬─────────┐" + System.lineSeparator() +
                        "│ 1 title │ 2 title │ 3 title │" + System.lineSeparator() +
                        "│─────────┼─────────┼─────────│" + System.lineSeparator() +
                        "└─────────┴─────────┴─────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title")),
                new ArrayList<>(), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testOneItemToPrint_onlyTableItems() {
        String expectedString =
                "" +
                        "┌────────┐" + System.lineSeparator() +
                        "│ ~~     │" + System.lineSeparator() +
                        "│────────│" + System.lineSeparator() +
                        "│ 1 item │" + System.lineSeparator() +
                        "└────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testTwoItemsToPrint_oneLineTableItems() {
        String expectedString =
                "" +
                        "┌────────┬────────┐" + System.lineSeparator() +
                        "│ ~~     │ ~~     │" + System.lineSeparator() +
                        "│────────┼────────│" + System.lineSeparator() +
                        "│ 1 item │ 2 item │" + System.lineSeparator() +
                        "└────────┴────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testThreeItemsToPrint_oneLineTableItems() {
        String expectedString =
                "" +
                        "┌────────┬────────┬────────┐" + System.lineSeparator() +
                        "│ ~~     │ ~~     │ ~~     │" + System.lineSeparator() +
                        "│────────┼────────┼────────│" + System.lineSeparator() +
                        "│ 1 item │ 2 item │ 3 item │" + System.lineSeparator() +
                        "└────────┴────────┴────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item", "3 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testTwoLineItemsToPrint_oneItemForLine() {
        String expectedString =
                "" +
                        "┌────────┐" + System.lineSeparator() +
                        "│ ~~     │" + System.lineSeparator() +
                        "│────────│" + System.lineSeparator() +
                        "│ 1 item │" + System.lineSeparator() +
                        "│ 2 item │" + System.lineSeparator() +
                        "└────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item")),
                        new ArrayList<>(Arrays.asList("2 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testThreeLineItemsToPrint_oneItemForLine() {
        String expectedString =
                "" +
                        "┌────────┐" + System.lineSeparator() +
                        "│ ~~     │" + System.lineSeparator() +
                        "│────────│" + System.lineSeparator() +
                        "│ 1 item │" + System.lineSeparator() +
                        "│ 2 item │" + System.lineSeparator() +
                        "│ 3 item │" + System.lineSeparator() +
                        "└────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item")),
                        new ArrayList<>(Arrays.asList("2 item")),
                        new ArrayList<>(Arrays.asList("3 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testThreeLineItemsToPrint_twoItemsForLine() {
        String expectedString =
                "" +
                        "┌────────┬────────┐" + System.lineSeparator() +
                        "│ ~~     │ ~~     │" + System.lineSeparator() +
                        "│────────┼────────│" + System.lineSeparator() +
                        "│ 1 item │ 2 item │" + System.lineSeparator() +
                        "│ 3 item │ ~~     │" + System.lineSeparator() +
                        "│ 4 item │ ~~     │" + System.lineSeparator() +
                        "└────────┴────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item")),
                        new ArrayList<>(Arrays.asList("3 item")),
                        new ArrayList<>(Arrays.asList("4 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testThreeLineItemsToPrint_threeItemsForLine_diffLength() {
        String expectedString =
                "" +
                        "┌────────┬─────────────┬────────┐" + System.lineSeparator() +
                        "│ ~~     │ ~~          │ ~~     │" + System.lineSeparator() +
                        "│────────┼─────────────┼────────│" + System.lineSeparator() +
                        "│ 1 item │ 2 item_____ │ 3 item │" + System.lineSeparator() +
                        "│ 4 item │ 5 item      │ ~~     │" + System.lineSeparator() +
                        "│ 6 item │ ~~          │ ~~     │" + System.lineSeparator() +
                        "└────────┴─────────────┴────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item")),
                        new ArrayList<>(Arrays.asList("4 item", "5 item")),
                        new ArrayList<>(Arrays.asList("6 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testTableWithDiffParams() {
        String expectedString =
                "" +
                        "┌───────────┬─────────────┬─────────┬─────────┐" + System.lineSeparator() +
                        "│ 1 title   │ 2 title     │ 3 title │ 4 title │" + System.lineSeparator() +
                        "│───────────┼─────────────┼─────────┼─────────│" + System.lineSeparator() +
                        "│ 1 item    │ 2 item_____ │ 3 item  │ 4 item  │" + System.lineSeparator() +
                        "│ 4 item    │ 5 item      │ ~~      │ ~~      │" + System.lineSeparator() +
                        "│ 6 item___ │ ~~          │ ~~      │ ~~      │" + System.lineSeparator() +
                        "└───────────┴─────────────┴─────────┴─────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title", "4 title")),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item")),
                        new ArrayList<>(Arrays.asList("4 item", "5 item")),
                        new ArrayList<>(Arrays.asList("6 item___")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testTableWithDiffParams_2() {
        String expectedString =
                "" +
                        "┌─────────┬─────────────┬────────────┬─────────┬────────┐" + System.lineSeparator() +
                        "│ 1 title │ 2 title     │ 3 title___ │ 4 title │ ~~     │" + System.lineSeparator() +
                        "│─────────┼─────────────┼────────────┼─────────┼────────│" + System.lineSeparator() +
                        "│ 1 item  │ 2 item_____ │ 3 item     │ 4 item  │ 5 item │" + System.lineSeparator() +
                        "│ 4 item  │ 5 item      │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                        "│ 6 item  │ ~~          │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                        "└─────────┴─────────────┴────────────┴─────────┴────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title___", "4 title")),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item", "5 item")),
                        new ArrayList<>(Arrays.asList("4 item", "5 item")),
                        new ArrayList<>(Arrays.asList("6 item")))), false);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testPrintTableWithIndex() {
        String expectedString =
                "" +
                        "┌───┬─────────┬─────────────┬────────────┬─────────┬────────┐" + System.lineSeparator() +
                        "│ # │ 1 title │ 2 title     │ 3 title___ │ 4 title │ ~~     │" + System.lineSeparator() +
                        "│───┼─────────┼─────────────┼────────────┼─────────┼────────│" + System.lineSeparator() +
                        "│ 1 │ 1 item  │ 2 item_____ │ 3 item     │ 4 item  │ 5 item │" + System.lineSeparator() +
                        "│ 2 │ 4 item  │ 5 item      │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                        "│ 3 │ 6 item  │ ~~          │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                        "└───┴─────────┴─────────────┴────────────┴─────────┴────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title___", "4 title")),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item", "5 item")),
                        new ArrayList<>(Arrays.asList("4 item", "5 item")),
                        new ArrayList<>(Arrays.asList("6 item")))), true);
        assertEquals(expectedString, baos.toString().trim());
    }

    @Test
    void testPrintTableWithIndex_sizableWidthForIndexColumn() {
        String expectedString =
                "" +
                        "┌────┬─────────┐" + System.lineSeparator() +
                        "│ #  │ 1 title │" + System.lineSeparator() +
                        "│────┼─────────│" + System.lineSeparator() +
                        "│ 1  │ 1 item  │" + System.lineSeparator() +
                        "│ 2  │ 2 item  │" + System.lineSeparator() +
                        "│ 3  │ 3 item  │" + System.lineSeparator() +
                        "│ 4  │ 4 item  │" + System.lineSeparator() +
                        "│ 5  │ 5 item  │" + System.lineSeparator() +
                        "│ 6  │ 6 item  │" + System.lineSeparator() +
                        "│ 7  │ 7 item  │" + System.lineSeparator() +
                        "│ 8  │ 8 item  │" + System.lineSeparator() +
                        "│ 9  │ 9 item  │" + System.lineSeparator() +
                        "│ 10 │ 10 item │" + System.lineSeparator() +
                        "└────┴─────────┘";
        TablePrinter.printTable(printWriter,
                new ArrayList<>(Arrays.asList("1 title")),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("1 item")),
                        new ArrayList<>(Arrays.asList("2 item")),
                        new ArrayList<>(Arrays.asList("3 item")),
                        new ArrayList<>(Arrays.asList("4 item")),
                        new ArrayList<>(Arrays.asList("5 item")),
                        new ArrayList<>(Arrays.asList("6 item")),
                        new ArrayList<>(Arrays.asList("7 item")),
                        new ArrayList<>(Arrays.asList("8 item")),
                        new ArrayList<>(Arrays.asList("9 item")),
                        new ArrayList<>(Arrays.asList("10 item")))), true);
        assertEquals(expectedString, baos.toString().trim());
    }
}