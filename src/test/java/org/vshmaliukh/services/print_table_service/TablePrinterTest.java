package org.vshmaliukh.services.print_table_service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TablePrinterTest {

    @Test
    void test(){

        List<String> stringList1 = new ArrayList<>(Arrays.asList("0","1","2","3"));
        List<String> stringList2 = new ArrayList<>(Arrays.asList("0","1","2","3"));
        List<String> stringList3 = new ArrayList<>(Arrays.asList("0","1","2","3"));

        List<List<String>> tableList = new ArrayList<>();
        tableList.add(stringList1);
        tableList.add(stringList2);
        tableList.add(stringList3);

        TablePrinter tablePrinter = new TablePrinter();


        tablePrinter.printTable(tableList);
    }

}