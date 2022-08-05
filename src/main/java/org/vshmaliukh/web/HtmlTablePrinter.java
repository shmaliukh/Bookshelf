package org.vshmaliukh.web;

import org.vshmaliukh.terminal.services.print_table_service.AbstractTablePrinter;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class HtmlTablePrinter extends AbstractTablePrinter {

    private boolean isForEditing;

    public HtmlTablePrinter(PrintWriter printWriter, List<Map<String, String>> tableList, Boolean isNeedIndex, boolean isForEditing) {
        super(printWriter, tableList, isNeedIndex);
        this.isForEditing = isForEditing;
    }

    public void print() {
        printFormattedHTMLTable(isForEditing);
    }

    public void printFormattedHTMLTable(boolean isForEditing) {
        setUpValuesSettings();

        printWriter.println("<table style = \"border:1px solid black\">");
        printHTMLLine(titleList, isForEditing);
        tableListOfLists.forEach(o -> printHTMLLine(o, isForEditing));
        printWriter.println("</table>");
    }

    public String getLineHTMLString(List<String> stringList, boolean isForEditing) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isForEditing) {
            //stringBuilder.append("<form action = \"" + "title" + "\" method = \"POST\">\n");
        }
        stringBuilder.append("<tr style = \"border:1px solid black\">");
        for (String value : stringList) {
            stringBuilder.append("<td style = \"border:1px solid black\">");
            stringBuilder.append(value);
            stringBuilder.append("</td>");
        }
        if (isForEditing) {
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

    private void printHTMLLine(List<String> stringList, boolean isForEditing) {
        printWriter.printf(getLineHTMLString(stringList, isForEditing));
    }

}
