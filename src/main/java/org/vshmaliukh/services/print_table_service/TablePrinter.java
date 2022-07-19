package org.vshmaliukh.services.print_table_service;

import lombok.Data;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.constants.ConstantsForTablePrinter.*;

@Data
public class TablePrinter {

    private final PrintWriter printWriter;

    private final List<String> titleList;
    private final List<List<String>> tableList;

    private List<Integer> sizeList = new ArrayList<>();

    private boolean isNeedIndex = false;

    private TablePrinter(PrintWriter printWriter, List<String> titleList, List<List<String>> tableList, Boolean isNeedIndex) {
        this.printWriter = printWriter;
        this.isNeedIndex = isNeedIndex;
        this.titleList = new ArrayList<>(titleList);
        this.tableList = new ArrayList<>();
        for (List<String> stringList : tableList) {
            this.tableList.add(new ArrayList<>(stringList));
        }
    }

    public static void printTable(PrintWriter printWriter, List<String> titleList, List<List<String>> tableList, boolean isNeedIndex) {
        TablePrinter tablePrinter = new TablePrinter(printWriter, titleList, tableList, isNeedIndex);
        tablePrinter.printFormattedTable();
    }

    public void appendTableWithDefaultValues() {
        int maxSize = titleList.size();
        for (List<String> stringList : tableList) {
            maxSize = Math.max(stringList.size(), maxSize);
        }
        appendWithDefaultValuesList(titleList, maxSize);
        for (List<String> stringList : tableList) {
            appendWithDefaultValuesList(stringList, maxSize);
        }
    }

    private void appendWithDefaultValuesList(List<String> stringList, int max) {
        while (stringList.size() < max) {
            stringList.add(EMPTY_ITEM_VALUE);
        }
    }

    public void countMaxSpaceWidth() {
        sizeList = new ArrayList<>();
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

    private void printFormattedTable() {
        setUpValuesSettings();

        printLine('┌', '┬', '┐');
        printLine(titleList);
        printLine('│', '┼', '│');
        tableList.forEach(this::printLine);
        printLine('└', '┴', '┘');
    }

    private void setUpValuesSettings() {
        if (isNeedIndex) {
            addIndexBeforeLines();
        }
        appendTableWithDefaultValues();
        countMaxSpaceWidth();
        appendEmptyItemsForTable();
    }

    private void addIndexBeforeLines() {
        titleList.add(0, INDEX_VALUE);
        for (int counter = 0; counter < tableList.size(); counter++) {
            tableList.get(counter).add(0, String.valueOf(counter + 1));
        }
    }

    private void printLine(char startSymbol, char crossedSymbol, char endSymbol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startSymbol);
        for (int i = 0; i < sizeList.size(); i++) {
            for (int j = 0; j < sizeList.get(i) + 2; j++) {
                stringBuilder.append(LINE_VALUE);
            }
            if (i < sizeList.size() - 1) {
                stringBuilder.append(crossedSymbol);
            }
        }
        stringBuilder.append(endSymbol);
        printWriter.println(stringBuilder);
    }

    private void appendEmptyItemsForTable() {
        fillStringListWithSpaces(titleList);
        for (List<String> stringList : tableList) {
            fillStringListWithSpaces(stringList);
        }
    }

    private void fillStringListWithSpaces(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            while (stringList.get(i).length() < sizeList.get(i)) {
                stringList.set(i, stringList.get(i) + SPACE_VALUE);
            }
        }
    }

    private void printLine(List<String> stringList) {
        printWriter.println(getLineString(stringList));
    }

    public String getLineString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SEPARATOR_VALUE);
        for (String value : stringList) {
            stringBuilder.append(SPACE_VALUE);
            stringBuilder.append(value);
            stringBuilder.append(SPACE_VALUE);
            stringBuilder.append(SEPARATOR_VALUE);
        }
        return stringBuilder.toString();
    }
}

