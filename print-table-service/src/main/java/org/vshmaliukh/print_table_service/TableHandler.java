package org.vshmaliukh.print_table_service;

import java.util.*;

public class TableHandler {

    protected final boolean isNeedIndex;

    protected List<String> titleList;
    protected List<List<String>> tableListOfLists;
    protected List<Map<String, String>> bufferTableListOfMaps;

    public TableHandler(List<Map<String, String>> bufferTableListOfMaps, Boolean isNeedIndex) {

        this.isNeedIndex = isNeedIndex;
        this.bufferTableListOfMaps = bufferTableListOfMaps;
    }

    public TableHandler(List<Map<String, String>> bufferTableListOfMaps, List<String> titleList, Boolean isNeedIndex) {
        this.titleList = titleList;
        this.isNeedIndex = isNeedIndex;
        this.bufferTableListOfMaps = bufferTableListOfMaps;
    }

    public List<String> getGeneratedTitleList() {
        formTable();
        return new ArrayList<>(titleList);
    }

    public List<List<String>> getGeneratedTableList() {
        formTable();
        return new ArrayList<>(tableListOfLists);
    }

    protected void formTable() {
        initTitles();
        initTable();
        if (isNeedIndex) {
            addIndexBeforeLines();
        }
    }

    protected void addIndexBeforeLines() {
        titleList.add(0, "#");
        for (int counter = 0; counter < tableListOfLists.size(); counter++) {
            tableListOfLists.get(counter).add(0, String.valueOf(counter + 1));
        }
    }

    protected void initTable() {
        //fixme is useless ????????
//        List<Map<String, String>> buffTable = new ArrayList<>();
//        for (Map<String, String> stringMap : bufferTableListOfMaps) {
//            buffTable.add(new HashMap<>(stringMap));
//        }
        fillTableByTitles();
    }

    protected void fillTableByTitles() {
        tableListOfLists = new ArrayList<>();
        for (Map<String, String> titleValueMap : bufferTableListOfMaps) {
            List<String> row = new ArrayList<>();
            for (String title : titleList) {
                row.add(titleValueMap.getOrDefault(title, "~~"));
            }
            tableListOfLists.add(row);
        }
    }

    protected void initTitles() {
        Set<String> buffSet = new HashSet<>();
        for (Map<String, String> titleValueMap : bufferTableListOfMaps) {
            buffSet.addAll(titleValueMap.keySet());
        }
        if (this.titleList != null && !this.titleList.isEmpty()) {
            List<String> list = new ArrayList<>();
            for (String s : this.titleList) {
                if (buffSet.contains(s)) {
                    list.add(s);
                }
            }
            this.titleList = list;
        } else {
            this.titleList = new ArrayList<>(buffSet);
            Collections.sort(titleList);
        }
    }

}
