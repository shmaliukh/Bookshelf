package org.vshmaliukh.services.print_table_service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TablePrinterTest {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    PrintWriter printWriter = new PrintWriter(baos, true);

    @ParameterizedTest
    @MethodSource("provideItemsForTablePrint")
    void testPrintTable(List<Map<String, String>> providedTable, String expected) {
        TablePrinter.printTable(printWriter, providedTable, false);
        assertEquals(expected, baos.toString().trim());
    }

    @ParameterizedTest
    @MethodSource("provideItemsForTablePrintWithIndex")
    void testPrintTableWithIndex(List<Map<String, String>> providedTable, String expected) {
        TablePrinter.printTable(printWriter, providedTable, true);
        assertEquals(expected, baos.toString().trim());
    }

    private static Stream<Arguments> provideItemsForTablePrint() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("t1", "v1");
        map1.put("t2", "v2");
        map1.put("t3", "v3");
        map1.put("t4", "v4");
        Map<String, String> map2 = new HashMap<>();
        map2.put("t1", "_v1");
        map2.put("t4", "_v4");
        map2.put("t5", "_v5");
        Map<String, String> map3 = new HashMap<>();
        map3.put("t7", "_v7_");
        return Stream.of(
                Arguments.of(
                        Arrays.asList(map1, map2, map3)
                        ,
                        "" +
                        "┌─────┬────┬────┬─────┬─────┬──────┐" + System.lineSeparator() +
                        "│ t1  │ t2 │ t3 │ t4  │ t5  │ t7   │" + System.lineSeparator() +
                        "│─────┼────┼────┼─────┼─────┼──────│" + System.lineSeparator() +
                        "│ v1  │ v2 │ v3 │ v4  │ ~~  │ ~~   │" + System.lineSeparator() +
                        "│ _v1 │ ~~ │ ~~ │ _v4 │ _v5 │ ~~   │" + System.lineSeparator() +
                        "│ ~~  │ ~~ │ ~~ │ ~~  │ ~~  │ _v7_ │" + System.lineSeparator() +
                        "└─────┴────┴────┴─────┴─────┴──────┘"
                ),
                Arguments.of(
                        Arrays.asList(map1, map2)
                        ,
                        "" +
                        "┌─────┬────┬────┬─────┬─────┐" + System.lineSeparator() +
                        "│ t1  │ t2 │ t3 │ t4  │ t5  │" + System.lineSeparator() +
                        "│─────┼────┼────┼─────┼─────│" + System.lineSeparator() +
                        "│ v1  │ v2 │ v3 │ v4  │ ~~  │" + System.lineSeparator() +
                        "│ _v1 │ ~~ │ ~~ │ _v4 │ _v5 │" + System.lineSeparator() +
                        "└─────┴────┴────┴─────┴─────┘"
                ),
                Arguments.of(
                        Collections.singletonList(map2)
                        ,
                        "" +
                                "┌─────┬─────┬─────┐" + System.lineSeparator() +
                                "│ t1  │ t4  │ t5  │" + System.lineSeparator() +
                                "│─────┼─────┼─────│" + System.lineSeparator() +
                                "│ _v1 │ _v4 │ _v5 │" + System.lineSeparator() +
                                "└─────┴─────┴─────┘"
                ),
                Arguments.of(
                        Collections.singletonList(map1)
                        ,
                        "" +
                                "┌────┬────┬────┬────┐" + System.lineSeparator() +
                                "│ t1 │ t2 │ t3 │ t4 │" + System.lineSeparator() +
                                "│────┼────┼────┼────│" + System.lineSeparator() +
                                "│ v1 │ v2 │ v3 │ v4 │" + System.lineSeparator() +
                                "└────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                map1,
                                map1)
                        ,
                        "" +
                                "┌────┬────┬────┬────┐" + System.lineSeparator() +
                                "│ t1 │ t2 │ t3 │ t4 │" + System.lineSeparator() +
                                "│────┼────┼────┼────│" + System.lineSeparator() +
                                "│ v1 │ v2 │ v3 │ v4 │" + System.lineSeparator() +
                                "│ v1 │ v2 │ v3 │ v4 │" + System.lineSeparator() +
                                "└────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("2 title__________", "v2"))
                        ,
                        "" +
                                "┌─────────┬───────────────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title__________ │" + System.lineSeparator() +
                                "│─────────┼───────────────────│" + System.lineSeparator() +
                                "│ v1      │ ~~                │" + System.lineSeparator() +
                                "│ ~~      │ v2                │" + System.lineSeparator() +
                                "└─────────┴───────────────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2__________"))
                        ,
                        "" +
                                "┌──────────────┐" + System.lineSeparator() +
                                "│ 1 title      │" + System.lineSeparator() +
                                "│──────────────│" + System.lineSeparator() +
                                "│ v1           │" + System.lineSeparator() +
                                "│ v2__________ │" + System.lineSeparator() +
                                "└──────────────┘"
                ),
                Arguments.of(
                        Collections.singletonList(
                                Collections.singletonMap("1 title", "v1__________"))
                        ,
                        "" +
                                "┌──────────────┐" + System.lineSeparator() +
                                "│ 1 title      │" + System.lineSeparator() +
                                "│──────────────│" + System.lineSeparator() +
                                "│ v1__________ │" + System.lineSeparator() +
                                "└──────────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2"),
                                Collections.singletonMap("1 title", "v3"),
                                Collections.singletonMap("2 title", "v4"),
                                Collections.singletonMap("2 title", "v5"))
                        ,
                        "" +
                                "┌─────────┬─────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title │" + System.lineSeparator() +
                                "│─────────┼─────────│" + System.lineSeparator() +
                                "│ v1      │ ~~      │" + System.lineSeparator() +
                                "│ v2      │ ~~      │" + System.lineSeparator() +
                                "│ v3      │ ~~      │" + System.lineSeparator() +
                                "│ ~~      │ v4      │" + System.lineSeparator() +
                                "│ ~~      │ v5      │" + System.lineSeparator() +
                                "└─────────┴─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("2 title", "v2"),
                                Collections.singletonMap("3 title", "v3"))
                        ,
                        "" +
                                "┌─────────┬─────────┬─────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title │ 3 title │" + System.lineSeparator() +
                                "│─────────┼─────────┼─────────│" + System.lineSeparator() +
                                "│ v1      │ ~~      │ ~~      │" + System.lineSeparator() +
                                "│ ~~      │ v2      │ ~~      │" + System.lineSeparator() +
                                "│ ~~      │ ~~      │ v3      │" + System.lineSeparator() +
                                "└─────────┴─────────┴─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2"),
                                Collections.singletonMap("1 title", "v3"))
                        ,
                        "" +
                                "┌─────────┐" + System.lineSeparator() +
                                "│ 1 title │" + System.lineSeparator() +
                                "│─────────│" + System.lineSeparator() +
                                "│ v1      │" + System.lineSeparator() +
                                "│ v2      │" + System.lineSeparator() +
                                "│ v3      │" + System.lineSeparator() +
                                "└─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2"))
                        ,
                        "" +
                                "┌─────────┐" + System.lineSeparator() +
                                "│ 1 title │" + System.lineSeparator() +
                                "│─────────│" + System.lineSeparator() +
                                "│ v1      │" + System.lineSeparator() +
                                "│ v2      │" + System.lineSeparator() +
                                "└─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "-"),
                                Collections.singletonMap("2 title", "-"))
                        ,
                        "" +
                                "┌─────────┬─────────┐" + System.lineSeparator() +
                                "│ 1 title │ 2 title │" + System.lineSeparator() +
                                "│─────────┼─────────│" + System.lineSeparator() +
                                "│ -       │ ~~      │" + System.lineSeparator() +
                                "│ ~~      │ -       │" + System.lineSeparator() +
                                "└─────────┴─────────┘"
                ),
                Arguments.of(
                        Collections.singletonList(
                                Collections.singletonMap("1 title", ""))
                        ,
                        "" +
                                "┌─────────┐" + System.lineSeparator() +
                                "│ 1 title │" + System.lineSeparator() +
                                "│─────────│" + System.lineSeparator() +
                                "│         │" + System.lineSeparator() +
                                "└─────────┘"
                )
        );
    }

    private static Stream<Arguments> provideItemsForTablePrintWithIndex() {
        Map<String, String> map = new HashMap<>();
        map.put("t1", "v1");
        map.put("t2", "v2");
        map.put("t3", "v3");
        map.put("t4", "v4");
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                map,
                                map)
                        ,
                        "" +
                                "┌───┬────┬────┬────┬────┐" + System.lineSeparator() +
                                "│ # │ t1 │ t2 │ t3 │ t4 │" + System.lineSeparator() +
                                "│───┼────┼────┼────┼────│" + System.lineSeparator() +
                                "│ 1 │ v1 │ v2 │ v3 │ v4 │" + System.lineSeparator() +
                                "│ 2 │ v1 │ v2 │ v3 │ v4 │" + System.lineSeparator() +
                                "└───┴────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Collections.singletonList(map)
                        ,
                        "" +
                                "┌───┬────┬────┬────┬────┐" + System.lineSeparator() +
                                "│ # │ t1 │ t2 │ t3 │ t4 │" + System.lineSeparator() +
                                "│───┼────┼────┼────┼────│" + System.lineSeparator() +
                                "│ 1 │ v1 │ v2 │ v3 │ v4 │" + System.lineSeparator() +
                                "└───┴────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2"),
                                Collections.singletonMap("1 title", "v3"),
                                Collections.singletonMap("1 title", "v4"),
                                Collections.singletonMap("1 title", "v5"),
                                Collections.singletonMap("1 title", "v6"),
                                Collections.singletonMap("1 title", "v7"),
                                Collections.singletonMap("1 title", "v8"),
                                Collections.singletonMap("1 title", "v9"),
                                Collections.singletonMap("1 title", "v10"))
                        ,
                        "" +
                                "┌────┬─────────┐" + System.lineSeparator() +
                                "│ #  │ 1 title │" + System.lineSeparator() +
                                "│────┼─────────│" + System.lineSeparator() +
                                "│ 1  │ v1      │" + System.lineSeparator() +
                                "│ 2  │ v2      │" + System.lineSeparator() +
                                "│ 3  │ v3      │" + System.lineSeparator() +
                                "│ 4  │ v4      │" + System.lineSeparator() +
                                "│ 5  │ v5      │" + System.lineSeparator() +
                                "│ 6  │ v6      │" + System.lineSeparator() +
                                "│ 7  │ v7      │" + System.lineSeparator() +
                                "│ 8  │ v8      │" + System.lineSeparator() +
                                "│ 9  │ v9      │" + System.lineSeparator() +
                                "│ 10 │ v10     │" + System.lineSeparator() +
                                "└────┴─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("2 title__________", "v2"))
                        ,
                        "" +
                                "┌───┬─────────┬───────────────────┐" + System.lineSeparator() +
                                "│ # │ 1 title │ 2 title__________ │" + System.lineSeparator() +
                                "│───┼─────────┼───────────────────│" + System.lineSeparator() +
                                "│ 1 │ v1      │ ~~                │" + System.lineSeparator() +
                                "│ 2 │ ~~      │ v2                │" + System.lineSeparator() +
                                "└───┴─────────┴───────────────────┘"
                )
        );
    }
}