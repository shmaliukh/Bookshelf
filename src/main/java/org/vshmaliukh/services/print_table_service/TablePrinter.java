package org.vshmaliukh.services.print_table_service;

import java.io.PrintWriter;
import java.util.List;

public class TablePrinter {

    private static final String ITEM_SEPARATOR = "|";

    PrintWriter printWriter;

    List<String> titleList;
    List<List<String>> tableList;

    public TablePrinter(PrintWriter printWriter, List<String> titleList, List<List<String>> tableList) {
        this.printWriter = printWriter;
        this.titleList = titleList;
        this.tableList = tableList;
    }

    public TablePrinter(List<String> titleList, List<List<String>> tableList) {
        this.printWriter = new PrintWriter(System.out, true);
        this.titleList = titleList;
        this.tableList = tableList;
    }

    void printTable() {
        tableList.forEach(this::printLine);
    }

    void printLine(List<String> stringList) {
        printWriter.print(ITEM_SEPARATOR);
        for (String s : stringList) {
            printWriter.print(" " + s + " " + ITEM_SEPARATOR);
        }
    }


}
