package org.vshmaliukh.print_table_service;

import java.util.*;

public class BaseTableHandler extends TableGenerator{
    protected List<Integer> sizeList = new ArrayList<>();

    public BaseTableHandler(List<Map<String, String>> bufferTableListOfMaps, Boolean isNeedIndex) {
        super(bufferTableListOfMaps,isNeedIndex);
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

    protected void setUpValuesSettings() {
        formTable();
        countMaxSpaceWidth();
        appendEmptyItemsForTable();
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
}