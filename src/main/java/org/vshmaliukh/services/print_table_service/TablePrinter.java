package org.vshmaliukh.services.print_table_service;

import lombok.Data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
public class TablePrinter {

    public static final String ITEM_SEPARATOR = "|";
    public static final String ITEM_SPACE = " ";

    String format;

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

    public void sortTable(){
          Collections.sort(tableList, new ListComparator<>());
    }

    public void initFormat() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> sizeList = new ArrayList<>();

        //tableList.forEach();
        // TODO

        format = stringBuilder.toString();
    }

    void initFormat(List<List<String>> customTableList) {
        List<Integer> sizeList = getMaxSpaceSizeListForItems(customTableList);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ITEM_SEPARATOR);
        for (Integer spacesNumber : sizeList) {
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append("%");
            stringBuilder.append(spacesNumber);
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(ITEM_SEPARATOR);
        }
        format = stringBuilder.toString();
    }

    private List<Integer> getMaxSpaceSizeListForItems(List<List<String>> customTableList) {
        List<Integer> integerList = new ArrayList<>();
        List<String> firstStrList = customTableList.get(0); // TODO create sorted method where first value (str.length) is the largest one
        for (String s : firstStrList) {
            integerList.add(s.length());
        }
        return integerList;
    }

    void printTable() {
        initFormat(tableList);
        tableList.forEach(this::printLine);
    }

    void printTable(List<List<String>> customTableList) {
        initFormat(customTableList);
        customTableList.forEach(this::printLine);
    }

    void printLine(List<String> stringList) {
        printWriter.println(getLineString(stringList));
    }

    String getLineString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ITEM_SEPARATOR);
        for (String value : stringList) {
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(value);
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(ITEM_SEPARATOR);
        }

        return String.format(format, stringBuilder);
    }

    String getLineString(List<String> stringList, String formatStr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ITEM_SEPARATOR);
        for (String value : stringList) {
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(value);
            stringBuilder.append(ITEM_SPACE);
            stringBuilder.append(ITEM_SEPARATOR);
        }

        initFormat(Collections.singletonList(stringList));
        formatStr = format; // TODO extract as separate
        return String.format(formatStr, stringBuilder);
    }
}

class ListComparator<T extends Comparable<T>> implements Comparator<List<T>>{

    @Override
    public int compare(List<T> list1, List<T> list2) {
        for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
            int c = list1.get(i).compareTo(list2.get(i));
            if (c != 0) {
                return c;
            }
        }
        return -Integer.compare(list1.size(), list2.size());
    }
}