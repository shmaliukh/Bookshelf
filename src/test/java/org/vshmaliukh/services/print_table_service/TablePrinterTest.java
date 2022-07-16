package org.vshmaliukh.services.print_table_service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.services.print_table_service.TablePrinter.ITEM_SEPARATOR;
import static org.vshmaliukh.services.print_table_service.TablePrinter.ITEM_SPACE;

class TablePrinterTest {

    TablePrinter tablePrinter = new TablePrinter();

    @Test
    void testGetOneItemLine(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item"));

        String expectedStr = ITEM_SEPARATOR + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.getLineString(stringList));
    }

    @Test
    void testGetTwoItemsLine(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item", "1 item"));

        String expectedStr = ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(1) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.getLineString(stringList));
    }

    @Test
    void testGetThreeItemsLine(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item", "1 item", "2 item"));

        String expectedStr = ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(1) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(2) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.getLineString(stringList));
    }


    @Test
    void test(){

        List<String> stringList1 = new ArrayList<>(Arrays.asList("0   ","1","2","3"));
        List<String> stringList2 = new ArrayList<>(Arrays.asList("0","1","2","3"));
        List<String> stringList3 = new ArrayList<>(Arrays.asList("0","1","2","3"));

        List<List<String>> tableList = new ArrayList<>();
        tableList.add(stringList1);
        tableList.add(stringList2);
        tableList.add(stringList3);




        tablePrinter.printTable(tableList);
    }

}