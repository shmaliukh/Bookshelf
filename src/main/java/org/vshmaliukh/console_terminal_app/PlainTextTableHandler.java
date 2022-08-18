package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.services.print_table_service.AbstractTableHandler;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class PlainTextTableHandler extends AbstractTableHandler {

    protected final PrintWriter printWriter;

    public PlainTextTableHandler(PrintWriter printWriter, List<Map<String, String>> tableList, Boolean isNeedIndex) {
        super(tableList, isNeedIndex);
        this.printWriter = printWriter;
    }

    public PlainTextTableHandler(PrintWriter printWriter, List<String> titleList, List<Map<String, String>> tableList, Boolean isNeedIndex) {
        super(tableList, isNeedIndex);
        super.titleList = titleList;
        this.printWriter = printWriter;
    }

    public void print() {
        setUpValuesSettings();

        printLine("┌", "─", "┐");
        printLine(titleList);
        printLine("├", "┼", "┤");
        tableListOfLists.forEach(this::printLine);
        printLine("└", "─", "┘");
    }

    protected void printLine(String startSymbol, String crossedSymbol, String endSymbol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startSymbol);
        for (int i = 0; i < sizeList.size(); i++) {
            for (int j = 0; j < sizeList.get(i) + 2; j++) {
                stringBuilder.append("│");
            }
            if (i < sizeList.size() - 1) {
                stringBuilder.append(crossedSymbol);
            }
        }

        stringBuilder.append(endSymbol);
        printWriter.println(stringBuilder);
    }

    protected void printLine(List<String> stringList) {
        printWriter.println(getLineString(stringList) );
    }

    public String getLineString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|");
        for (String value : stringList) {
            stringBuilder.append(" ");
            stringBuilder.append(value);
            stringBuilder.append(" ");
            stringBuilder.append("|");
        }
        return stringBuilder.toString();
    }
}
