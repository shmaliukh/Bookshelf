package org.vshmaliukh.services.print_table_service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TablePrinterTest {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    PrintWriter printWriter = new PrintWriter(baos, true);

    @ParameterizedTest
    @MethodSource("provideItemsForTablePrint")
    void testPrintTable(List<String> providedTitle, List<List<String>> providedTable, String expected) {
        TablePrinter.printTable(printWriter, providedTitle, providedTable, false);
        assertEquals(expected, baos.toString().trim());
    }

    @ParameterizedTest
    @MethodSource("provideItemsForTablePrintWithIndex")
    void testPrintTableWithIndex(List<String> providedTitle, List<List<String>> providedTable, String expected) {
        TablePrinter.printTable(printWriter, providedTitle, providedTable, true);
        assertEquals(expected, baos.toString().trim());
    }

    private static Stream<Arguments> provideItemsForTablePrint() {
        return Stream.of(
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title")),
                        new ArrayList<>()
                        ,
                        "" +
                                "┌─────────┐" + System.lineSeparator() +
                                "│ 1 title │" + System.lineSeparator() +
                                "│─────────│" + System.lineSeparator() +
                                "└─────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title", "2 title")),
                        new ArrayList<>()
                        ,
                        "" +
                                "┌─────────┬─────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title │" + System.lineSeparator() +
                                "│─────────┼─────────│" + System.lineSeparator() +
                                "└─────────┴─────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title")),
                        new ArrayList<>()
                        ,
                        "" +
                                "┌─────────┬─────────┬─────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title │ 3 title │" + System.lineSeparator() +
                                "│─────────┼─────────┼─────────│" + System.lineSeparator() +
                                "└─────────┴─────────┴─────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item")))
                        ),
                        "" +
                                "┌────────┐" + System.lineSeparator() +
                                "│ ~~     │" + System.lineSeparator() +
                                "│────────│" + System.lineSeparator() +
                                "│ 1 item │" + System.lineSeparator() +
                                "└────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item")))
                        ),
                        "" +
                                "┌────────┬────────┐" + System.lineSeparator() +
                                "│ ~~     │ ~~     │" + System.lineSeparator() +
                                "│────────┼────────│" + System.lineSeparator() +
                                "│ 1 item │ 2 item │" + System.lineSeparator() +
                                "└────────┴────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item", "3 item")))
                        ),
                        "" +
                                "┌────────┬────────┬────────┐" + System.lineSeparator() +
                                "│ ~~     │ ~~     │ ~~     │" + System.lineSeparator() +
                                "│────────┼────────┼────────│" + System.lineSeparator() +
                                "│ 1 item │ 2 item │ 3 item │" + System.lineSeparator() +
                                "└────────┴────────┴────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item")),
                                new ArrayList<>(Arrays.asList("2 item")))
                        ),
                        "" +
                                "┌────────┐" + System.lineSeparator() +
                                "│ ~~     │" + System.lineSeparator() +
                                "│────────│" + System.lineSeparator() +
                                "│ 1 item │" + System.lineSeparator() +
                                "│ 2 item │" + System.lineSeparator() +
                                "└────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item")),
                                new ArrayList<>(Arrays.asList("2 item")),
                                new ArrayList<>(Arrays.asList("3 item")))
                        ),
                        "" +
                                "┌────────┐" + System.lineSeparator() +
                                "│ ~~     │" + System.lineSeparator() +
                                "│────────│" + System.lineSeparator() +
                                "│ 1 item │" + System.lineSeparator() +
                                "│ 2 item │" + System.lineSeparator() +
                                "│ 3 item │" + System.lineSeparator() +
                                "└────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item")),
                                new ArrayList<>(Arrays.asList("3 item")),
                                new ArrayList<>(Arrays.asList("4 item")))
                        ),
                        "" +
                                "┌────────┬────────┐" + System.lineSeparator() +
                                "│ ~~     │ ~~     │" + System.lineSeparator() +
                                "│────────┼────────│" + System.lineSeparator() +
                                "│ 1 item │ 2 item │" + System.lineSeparator() +
                                "│ 3 item │ ~~     │" + System.lineSeparator() +
                                "│ 4 item │ ~~     │" + System.lineSeparator() +
                                "└────────┴────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item")),
                                new ArrayList<>(Arrays.asList("4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("6 item")))
                        ),
                        "" +
                                "┌────────┬─────────────┬────────┐" + System.lineSeparator() +
                                "│ ~~     │ ~~          │ ~~     │" + System.lineSeparator() +
                                "│────────┼─────────────┼────────│" + System.lineSeparator() +
                                "│ 1 item │ 2 item_____ │ 3 item │" + System.lineSeparator() +
                                "│ 4 item │ 5 item      │ ~~     │" + System.lineSeparator() +
                                "│ 6 item │ ~~          │ ~~     │" + System.lineSeparator() +
                                "└────────┴─────────────┴────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title", "4 title")),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item")),
                                new ArrayList<>(Arrays.asList("4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("6 item___")))
                        ),
                        "" +
                                "┌───────────┬─────────────┬─────────┬─────────┐" + System.lineSeparator() +
                                "│ 1 title   │ 2 title     │ 3 title │ 4 title │" + System.lineSeparator() +
                                "│───────────┼─────────────┼─────────┼─────────│" + System.lineSeparator() +
                                "│ 1 item    │ 2 item_____ │ 3 item  │ 4 item  │" + System.lineSeparator() +
                                "│ 4 item    │ 5 item      │ ~~      │ ~~      │" + System.lineSeparator() +
                                "│ 6 item___ │ ~~          │ ~~      │ ~~      │" + System.lineSeparator() +
                                "└───────────┴─────────────┴─────────┴─────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title___", "4 title")),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("6 item")))
                        ),
                        "" +
                                "┌─────────┬─────────────┬────────────┬─────────┬────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title     │ 3 title___ │ 4 title │ ~~     │" + System.lineSeparator() +
                                "│─────────┼─────────────┼────────────┼─────────┼────────│" + System.lineSeparator() +
                                "│ 1 item  │ 2 item_____ │ 3 item     │ 4 item  │ 5 item │" + System.lineSeparator() +
                                "│ 4 item  │ 5 item      │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                                "│ 6 item  │ ~~          │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                                "└─────────┴─────────────┴────────────┴─────────┴────────┘"
                )
        );
    }

    private static Stream<Arguments> provideItemsForTablePrintWithIndex() {
        return Stream.of(
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title___", "4 title")),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("6 item")))
                        ),
                        "" +
                                "┌───┬─────────┬─────────────┬────────────┬─────────┬────────┐" + System.lineSeparator() +
                                "│ # │ 1 title │ 2 title     │ 3 title___ │ 4 title │ ~~     │" + System.lineSeparator() +
                                "│───┼─────────┼─────────────┼────────────┼─────────┼────────│" + System.lineSeparator() +
                                "│ 1 │ 1 item  │ 2 item_____ │ 3 item     │ 4 item  │ 5 item │" + System.lineSeparator() +
                                "│ 2 │ 4 item  │ 5 item      │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                                "│ 3 │ 6 item  │ ~~          │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                                "└───┴─────────┴─────────────┴────────────┴─────────┴────────┘"
                ),
                Arguments.of(
                        new ArrayList<>(Arrays.asList("1 title", "2 title", "3 title___", "4 title")),
                        new ArrayList<>(Arrays.asList(
                                new ArrayList<>(Arrays.asList("1 item", "2 item_____", "3 item", "4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("4 item", "5 item")),
                                new ArrayList<>(Arrays.asList("6 item")))
                        ),
                        "" +
                                "┌───┬─────────┬─────────────┬────────────┬─────────┬────────┐" + System.lineSeparator() +
                                "│ # │ 1 title │ 2 title     │ 3 title___ │ 4 title │ ~~     │" + System.lineSeparator() +
                                "│───┼─────────┼─────────────┼────────────┼─────────┼────────│" + System.lineSeparator() +
                                "│ 1 │ 1 item  │ 2 item_____ │ 3 item     │ 4 item  │ 5 item │" + System.lineSeparator() +
                                "│ 2 │ 4 item  │ 5 item      │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                                "│ 3 │ 6 item  │ ~~          │ ~~         │ ~~      │ ~~     │" + System.lineSeparator() +
                                "└───┴─────────┴─────────────┴────────────┴─────────┴────────┘"
                ),
                Arguments.of(
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
                                new ArrayList<>(Arrays.asList("10 item")))
                        ),
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
                                "└────┴─────────┘"
                )
        );
    }
}