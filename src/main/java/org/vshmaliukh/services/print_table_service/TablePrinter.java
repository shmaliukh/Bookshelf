package org.vshmaliukh.services.print_table_service;

import lombok.Data;

import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Data
public class TablePrinter {

    private static final String ITEM_SEPARATOR = "|";
    private static final String ITEM_SPACE = " ";


    PrintWriter printWriter;

    List<String> titleList;
    List<List<String>> tableList;

    public TablePrinter() {
        this.printWriter = new PrintWriter(System.out, true);
        this.titleList = new ArrayList<>();
        this.tableList = new ArrayList<>();
    }

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

    void printTable(List<List<String>> customTableList) {
        customTableList.forEach(this::printLine);
    }

    void printLine(List<String> stringList) {
        printWriter.println(getLineString(stringList));
    }
    
    String getLineString (List<String> stringList){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ITEM_SEPARATOR);
        for (String value : stringList) {
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(value);
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(ITEM_SEPARATOR);
        }
        return stringBuilder.toString();
    }


}
