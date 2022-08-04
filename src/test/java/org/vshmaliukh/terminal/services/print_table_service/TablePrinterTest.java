package org.vshmaliukh.terminal.services.print_table_service;

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
        new PlainTextTablePrinter(printWriter, providedTable, false).print();
        assertEquals(expected, baos.toString().trim());
    }

    @ParameterizedTest
    @MethodSource("provideItemsForTablePrintWithIndex")
    void testPrintTableWithIndex(List<Map<String, String>> providedTable, String expected) {
        new PlainTextTablePrinter(printWriter, providedTable, true).print();
        assertEquals(expected, baos.toString().trim());
    }

    private static Stream<Arguments> provideItemsForTableprintln() {
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
                        "┌─────┬────┬────┬─────┬─────┬──────┐"  +
                        "│ t1  │ t2 │ t3 │ t4  │ t5  │ t7   │"  +
                        "│─────┼────┼────┼─────┼─────┼──────│"  +
                        "│ v1  │ v2 │ v3 │ v4  │ ~~  │ ~~   │"  +
                        "│ _v1 │ ~~ │ ~~ │ _v4 │ _v5 │ ~~   │"  +
                        "│ ~~  │ ~~ │ ~~ │ ~~  │ ~~  │ _v7_ │"  +
                        "└─────┴────┴────┴─────┴─────┴──────┘"
                ),
                Arguments.of(
                        Arrays.asList(map1, map2)
                        ,
                        "" +
                        "┌─────┬────┬────┬─────┬─────┐"  +
                        "│ t1  │ t2 │ t3 │ t4  │ t5  │"  +
                        "│─────┼────┼────┼─────┼─────│"  +
                        "│ v1  │ v2 │ v3 │ v4  │ ~~  │"  +
                        "│ _v1 │ ~~ │ ~~ │ _v4 │ _v5 │"  +
                        "└─────┴────┴────┴─────┴─────┘"
                ),
                Arguments.of(
                        Collections.singletonList(map2)
                        ,
                        "" +
                                "┌─────┬─────┬─────┐"  +
                                "│ t1  │ t4  │ t5  │"  +
                                "│─────┼─────┼─────│"  +
                                "│ _v1 │ _v4 │ _v5 │"  +
                                "└─────┴─────┴─────┘"
                ),
                Arguments.of(
                        Collections.singletonList(map1)
                        ,
                        "" +
                                "┌────┬────┬────┬────┐"  +
                                "│ t1 │ t2 │ t3 │ t4 │"  +
                                "│────┼────┼────┼────│"  +
                                "│ v1 │ v2 │ v3 │ v4 │"  +
                                "└────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                map1,
                                map1)
                        ,
                        "" +
                                "┌────┬────┬────┬────┐"  +
                                "│ t1 │ t2 │ t3 │ t4 │"  +
                                "│────┼────┼────┼────│"  +
                                "│ v1 │ v2 │ v3 │ v4 │"  +
                                "│ v1 │ v2 │ v3 │ v4 │"  +
                                "└────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("2 title__________", "v2"))
                        ,
                        "" +
                                "┌─────────┬───────────────────┐"  +
                                "│ 1 title │ 2 title__________ │"  +
                                "│─────────┼───────────────────│"  +
                                "│ v1      │ ~~                │"  +
                                "│ ~~      │ v2                │"  +
                                "└─────────┴───────────────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2__________"))
                        ,
                        "" +
                                "┌──────────────┐"  +
                                "│ 1 title      │"  +
                                "│──────────────│"  +
                                "│ v1           │"  +
                                "│ v2__________ │"  +
                                "└──────────────┘"
                ),
                Arguments.of(
                        Collections.singletonList(
                                Collections.singletonMap("1 title", "v1__________"))
                        ,
                        "" +
                                "┌──────────────┐"  +
                                "│ 1 title      │"  +
                                "│──────────────│"  +
                                "│ v1__________ │"  +
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
                                "┌─────────┬─────────┐"  +
                                "│ 1 title │ 2 title │"  +
                                "│─────────┼─────────│"  +
                                "│ v1      │ ~~      │"  +
                                "│ v2      │ ~~      │"  +
                                "│ v3      │ ~~      │"  +
                                "│ ~~      │ v4      │"  +
                                "│ ~~      │ v5      │"  +
                                "└─────────┴─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("2 title", "v2"),
                                Collections.singletonMap("3 title", "v3"))
                        ,
                        "" +
                                "┌─────────┬─────────┬─────────┐"  +
                                "│ 1 title │ 2 title │ 3 title │"  +
                                "│─────────┼─────────┼─────────│"  +
                                "│ v1      │ ~~      │ ~~      │"  +
                                "│ ~~      │ v2      │ ~~      │"  +
                                "│ ~~      │ ~~      │ v3      │"  +
                                "└─────────┴─────────┴─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2"),
                                Collections.singletonMap("1 title", "v3"))
                        ,
                        "" +
                                "┌─────────┐"  +
                                "│ 1 title │"  +
                                "│─────────│"  +
                                "│ v1      │"  +
                                "│ v2      │"  +
                                "│ v3      │"  +
                                "└─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("1 title", "v2"))
                        ,
                        "" +
                                "┌─────────┐"  +
                                "│ 1 title │"  +
                                "│─────────│"  +
                                "│ v1      │"  +
                                "│ v2      │"  +
                                "└─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "-"),
                                Collections.singletonMap("2 title", "-"))
                        ,
                        "" +
                                "┌─────────┬─────────┐"  +
                                "│ 1 title │ 2 title │"  +
                                "│─────────┼─────────│"  +
                                "│ -       │ ~~      │"  +
                                "│ ~~      │ -       │"  +
                                "└─────────┴─────────┘"
                ),
                Arguments.of(
                        Collections.singletonList(
                                Collections.singletonMap("1 title", ""))
                        ,
                        "" +
                                "┌─────────┐"  +
                                "│ 1 title │"  +
                                "│─────────│"  +
                                "│         │"  +
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
                                "┌───┬────┬────┬────┬────┐"  +
                                "│ # │ t1 │ t2 │ t3 │ t4 │"  +
                                "│───┼────┼────┼────┼────│"  +
                                "│ 1 │ v1 │ v2 │ v3 │ v4 │"  +
                                "│ 2 │ v1 │ v2 │ v3 │ v4 │"  +
                                "└───┴────┴────┴────┴────┘"
                ),
                Arguments.of(
                        Collections.singletonList(map)
                        ,
                        "" +
                                "┌───┬────┬────┬────┬────┐"  +
                                "│ # │ t1 │ t2 │ t3 │ t4 │"  +
                                "│───┼────┼────┼────┼────│"  +
                                "│ 1 │ v1 │ v2 │ v3 │ v4 │"  +
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
                                "┌────┬─────────┐"  +
                                "│ #  │ 1 title │"  +
                                "│────┼─────────│"  +
                                "│ 1  │ v1      │"  +
                                "│ 2  │ v2      │"  +
                                "│ 3  │ v3      │"  +
                                "│ 4  │ v4      │"  +
                                "│ 5  │ v5      │"  +
                                "│ 6  │ v6      │"  +
                                "│ 7  │ v7      │"  +
                                "│ 8  │ v8      │"  +
                                "│ 9  │ v9      │"  +
                                "│ 10 │ v10     │"  +
                                "└────┴─────────┘"
                ),
                Arguments.of(
                        Arrays.asList(
                                Collections.singletonMap("1 title", "v1"),
                                Collections.singletonMap("2 title__________", "v2"))
                        ,
                        "" +
                                "┌───┬─────────┬───────────────────┐"  +
                                "│ # │ 1 title │ 2 title__________ │"  +
                                "│───┼─────────┼───────────────────│"  +
                                "│ 1 │ v1      │ ~~                │"  +
                                "│ 2 │ ~~      │ v2                │"  +
                                "└───┴─────────┴───────────────────┘"
                )
        );
    }
}