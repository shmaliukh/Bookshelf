package org.vshmaliukh.terminal.services.print_table_service;

import java.io.PrintWriter;
import java.util.*;

public abstract class AbstractTablePrinter {

    protected final PrintWriter printWriter;
    protected final boolean isNeedIndex;

    protected List<String> titleList;
    protected List<List<String>> tableListOfLists;
    protected List<Map<String, String>> bufferTableListOfMaps;

    protected List<Integer> sizeList = new ArrayList<>();

    protected AbstractTablePrinter(PrintWriter printWriter, List<Map<String, String>> bufferTableListOfMaps, Boolean isNeedIndex) {
        this.printWriter = printWriter;
        this.isNeedIndex = isNeedIndex;
        this.bufferTableListOfMaps = bufferTableListOfMaps;
    }

    private void initTable(List<Map<String, String>> inputTable) {
        List<Map<String, String>> buffTable = new ArrayList<>();
        for (Map<String, String> stringMap : inputTable) {
            buffTable.add(new HashMap<>(stringMap));
        }
        fillTableByTitles(buffTable);
    }

    private void fillTableByTitles(List<Map<String, String>> buffTable) {
        tableListOfLists = new ArrayList<>();
        for (Map<String, String> titleValueMap : buffTable) {
            List<String> row = new ArrayList<>();
            for (String title : titleList) {
                row.add(titleValueMap.getOrDefault(title, "~~"));
            }
            tableListOfLists.add(row);
        }
    }

    private void initTitles(List<Map<String, String>> tableList) {
        Set<String> buffSet = new HashSet<>();
        for (Map<String, String> titleValueMap : tableList) {
            buffSet.addAll(titleValueMap.keySet());
        }
        if (this.titleList.isEmpty()) {
            this.titleList = new ArrayList<>(buffSet);
            Collections.sort(titleList);
        } else {
            List<String> list = new ArrayList<>();
            for (String s : this.titleList) {
                if (buffSet.contains(s)) {
                    list.add(s);
                }
            }
            this.titleList = list;
        }
    }

    public void countMaxSpaceWidth() {
        sizeList = new ArrayList<>();
        int strLength;
        titleList.forEach(s -> sizeList.add(s.length()));
        for (List<String> stringList : tableListOfLists) {
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

    public abstract void print();

    protected void setUpValuesSettings() {
        initTitles(bufferTableListOfMaps);

        initTable(bufferTableListOfMaps);

        if (isNeedIndex) {
            addIndexBeforeLines();
        }
        countMaxSpaceWidth();
        appendEmptyItemsForTable();
    }

    private void addIndexBeforeLines() {
        titleList.add(0, "#");
        for (int counter = 0; counter < tableListOfLists.size(); counter++) {
            tableListOfLists.get(counter).add(0, String.valueOf(counter + 1));
        }
    }

    private void appendEmptyItemsForTable() {
        fillStringListWithSpaces(titleList);
        tableListOfLists.forEach(this::fillStringListWithSpaces);
    }

    private void fillStringListWithSpaces(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            while (stringList.get(i).length() < sizeList.get(i)) {
                stringList.set(i, stringList.get(i) + " ");
            }
        }
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = new ArrayList<>(titleList);
    }
}