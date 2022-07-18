package org.vshmaliukh.services.print_table_service;

import lombok.Data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class TablePrinter {

    public static final String ITEM_SEPARATOR = "│";
    public static final String ITEM_SPACE = " ";
    public static final String EMPTY_VALUE = "~";

    PrintWriter printWriter;

    List<String> titleList;
    List<List<String>> tableList;

    List<Integer> sizeList = new ArrayList<>();

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

    public void sortTable() {
        tableList.sort(new ListComparator<>());
    }

    public void fillSortedTableWithEmptyValuesIfNecessary() { // TODO rename
        int max = Math.max(titleList.size(), tableList.get(0).size());
        for (List<String> stringList : tableList) {
            while (stringList.size() < max) {
                stringList.add(EMPTY_VALUE);
            }
        }
    }

    public void countMaxSpaceWidth() {
        int strLength;
        titleList.forEach(s -> sizeList.add(s.length()));
        for (List<String> stringList : tableList) {
            for (int i = 0; i < stringList.size(); i++) {
                strLength = stringList.get(i).length();
                if (sizeList.size() <= i) {
                    sizeList.add(strLength);
                } else if (sizeList.get(i) < strLength) {
                    sizeList.set(i, strLength);
                }
            }
        }
    }

    public void printTable() {
        setUpValuesSettings();

        printCustomLine('┌', '┬', '┐', '─');
        printLine(titleList);
        printCustomLine('│', '┼', '│', '─');
        tableList.forEach(this::printLine);
        printCustomLine('└', '┴', '┘', '─');
    }

    private void setUpValuesSettings() {
        sortTable();
        fillSortedTableWithEmptyValuesIfNecessary();
        countMaxSpaceWidth();
        fillAllValuesWithSpaces();
    }

    private void printCustomLine(char first, char middle, char last, char space) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(first);
        for (int i = 0; i < sizeList.size(); i++) {
            for (int j = 0; j < sizeList.get(i) + 2; j++) {
                stringBuilder.append(space);
            }
            if(i < sizeList.size()-1){
                stringBuilder.append(middle);
            }
        }
        stringBuilder.append(last);
        printWriter.println(stringBuilder);
    }

    private void fillAllValuesWithSpaces() {
        fillStringListWithSpaces(titleList);
        for (List<String> stringList : tableList) {
            fillStringListWithSpaces(stringList);
        }
    }

    private void fillStringListWithSpaces(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            while (stringList.get(i).length() < sizeList.get(i)) {
                stringList.set(i, stringList.get(i) + ITEM_SPACE);
            }
        }
    }

    void printLine(List<String> stringList) {
        printWriter.println(getLineString(stringList));
    }

    public String getLineString(List<String> stringList) {
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

class ListComparator<T extends Comparable<T>> implements Comparator<List<T>> {

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