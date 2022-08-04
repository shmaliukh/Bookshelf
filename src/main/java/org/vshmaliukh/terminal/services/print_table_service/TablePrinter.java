package org.vshmaliukh.terminal.services.print_table_service;

import lombok.Data;
import org.vshmaliukh.web.WebUtils;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.MESSAGE_ENTER_USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.EDIT_ITEMS_TITLE;

@Data
public class TablePrinter {

    private final PrintWriter printWriter;
    private final boolean isNeedIndex;

    private List<String> titleList;
    private List<List<String>> tableList;

    private List<Integer> sizeList = new ArrayList<>();


    TablePrinter(PrintWriter printWriter, List<Map<String, String>> tableList, Boolean isNeedIndex) {
        this.printWriter = printWriter;
        this.isNeedIndex = isNeedIndex;
        initTitles(tableList);
        initTable(tableList);
    }

    private void initTable(List<Map<String, String>> inputTable) {
        List<Map<String, String>> buffTable = new ArrayList<>();
        for (Map<String, String> stringMap : inputTable) {
            buffTable.add(new HashMap<>(stringMap));
        }
        fillTableByTitles(buffTable);
    }

    private void fillTableByTitles(List<Map<String, String>> buffTable) {
        tableList = new ArrayList<>();
        for (Map<String, String> titleValueMap : buffTable) {
            List<String> raw = new ArrayList<>();
            for (String title : titleList) {
                raw.add(titleValueMap.getOrDefault(title, "~~" ));
            }
            tableList.add(raw);
        }
    }

    private void initTitles(List<Map<String, String>> tableList) {
        Set<String> buffSet = new HashSet<>();
        for (Map<String, String> titleValueMap : tableList) {
            buffSet.addAll(titleValueMap.keySet());
        }
        this.titleList = new ArrayList<>(buffSet);
        Collections.sort(titleList);
    }

    public static void printTable(PrintWriter printWriter, List<Map<String, String>> tableList, boolean isNeedIndex) {
        // TODO is it necessary to keep this method as static one
        TablePrinter tablePrinter = new TablePrinter(printWriter, tableList, isNeedIndex);
        tablePrinter.printFormattedTable();
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


    public static void printHTMLTable(PrintWriter printWriter, List<Map<String, String>> tableList, boolean isNeedIndex, boolean isForEditing) {
        // TODO is it necessary to keep this method as static one
        TablePrinter tablePrinter = new TablePrinter(printWriter, tableList, isNeedIndex);
        tablePrinter.printFormattedHTMLTable(isForEditing);
    }

    public void printFormattedHTMLTable(boolean isForEditing) {
        setUpValuesSettings();
        printWriter.printf("<table style = \"border:1px solid black\">");
        printHTMLLine(titleList, isForEditing);
        tableList.forEach(o -> printHTMLLine(o, isForEditing));
        printWriter.printf("</table>");
    }

    public String getLineHTMLString(List<String> stringList, boolean isForEditing) {
        StringBuilder stringBuilder = new StringBuilder();
        if(isForEditing){
            //stringBuilder.append("<form action = \"" + "title" + "\" method = \"POST\">\n");
        }
        stringBuilder.append("<tr style = \"border:1px solid black\">");
        for (String value : stringList) {
            stringBuilder.append("<td style = \"border:1px solid black\">");
            stringBuilder.append(value);
            stringBuilder.append("</td>");
        }
        if(isForEditing){
            stringBuilder.append("<td style = \"border:1px solid black\">");

            //stringBuilder.append("<button " +
            //        "onclick=\"window.location.href='" +
            //        WebUtils.generateBaseURLBuilder(EDIT_ITEMS_TITLE) +
            //        "';\"> " +
            //        label +
            //        "</button> \n");
            //stringBuilder.append("<input type = \"\" name=\"" + "USER_NAME" + "\">\n ");
            //stringBuilder.append("<input type = \"submit\" value = \"Submit\" />\n");

            stringBuilder.append("</td>");
           //stringBuilder.append("</form>");

        }
        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }

    private void printHTMLLine(List<String> stringList, boolean isForEditing){
        printWriter.printf(getLineHTMLString(stringList, isForEditing));
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
        //appendTableWithDefaultValues();
        countMaxSpaceWidth();
        appendEmptyItemsForTable();
    }

    private void addIndexBeforeLines() {
        titleList.add(0, "#" );
        for (int counter = 0; counter < tableList.size(); counter++) {
            tableList.get(counter).add(0, String.valueOf(counter + 1));
        }
    }

    private void printLine(char startSymbol, char crossedSymbol, char endSymbol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startSymbol);
        for (int i = 0; i < sizeList.size(); i++) {
            for (int j = 0; j < sizeList.get(i) + 2; j++) {
                stringBuilder.append("─");
            }
            if (i < sizeList.size() - 1) {
                stringBuilder.append(crossedSymbol);
            }
        }

        stringBuilder.append(endSymbol);
        printWriter.println(stringBuilder );
    }

    private void appendEmptyItemsForTable() {
        fillStringListWithSpaces(titleList);
        tableList.forEach(this::fillStringListWithSpaces);
    }

    private void fillStringListWithSpaces(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            while (stringList.get(i).length() < sizeList.get(i)) {
                stringList.set(i, stringList.get(i) + " " );
            }
        }
    }

    private void printLine(List<String> stringList) {
        printWriter.println(getLineString(stringList) );
    }

    public String getLineString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("│");
        for (String value : stringList) {
            stringBuilder.append(" ");
            stringBuilder.append(value);
            stringBuilder.append(" ");
            stringBuilder.append("│");
        }
        return stringBuilder.toString();
    }


}

