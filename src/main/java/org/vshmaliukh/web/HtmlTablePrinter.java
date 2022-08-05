package org.vshmaliukh.web;

import org.vshmaliukh.terminal.services.print_table_service.AbstractTablePrinter;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.web.BookShelfWebApp.CHANGE_ITEM_BORROWED_STATE;
import static org.vshmaliukh.web.BookShelfWebApp.DELETE_ITEM_TITLE;
import static org.vshmaliukh.web.MainMenuServlet.TITLE_LIST;
import static org.vshmaliukh.web.menu_servlets.EditItemsServlet.INDEX_OF_ITEM;

public class HtmlTablePrinter extends AbstractTablePrinter {

    private boolean isForEditing;
    private final HttpServletRequest request;

    public HtmlTablePrinter(PrintWriter printWriter, List<Map<String, String>> tableList, Boolean isNeedIndex) {
        super(printWriter, tableList, isNeedIndex);
        super.titleList = TITLE_LIST;
        this.isForEditing = false;
        this.request = null;
    }

    public HtmlTablePrinter(PrintWriter printWriter, List<String> titleList, List<Map<String, String>> tableList, Boolean isNeedIndex) {
        super(printWriter, tableList, isNeedIndex);
        super.titleList = titleList;
        this.isForEditing = false;
        this.request = null;
    }

    public HtmlTablePrinter(PrintWriter printWriter, List<String> titleList, List<Map<String, String>> tableList, Boolean isNeedIndex, boolean isForEditing, HttpServletRequest request) {
        super(printWriter, tableList, isNeedIndex);
        super.titleList = titleList;
        this.isForEditing = isForEditing;
        this.request = request;
    }

    public void print() {
        printFormattedHTMLTable(isForEditing);
    }

    @Override
    protected void setUpValuesSettings() {
        initTitles(bufferTableListOfMaps);
        initTable(bufferTableListOfMaps);
        if (isNeedIndex) {
            addIndexBeforeLines();
        }
    }

    public void printFormattedHTMLTable(boolean isForEditing) {
        setUpValuesSettings();

        printWriter.printf("<table style = \"border:1px solid black\">");
        printHTMLLine(titleList, false);
        tableListOfLists.forEach(o -> printHTMLLine(o, isForEditing));
        printWriter.printf("</table>");
    }

    public String getLineHTMLString(List<String> stringList, boolean isForEditing) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<tr style = \"border:1px solid black\">");
        for (String value : stringList) {
            stringBuilder.append("<td style = \"border:1px solid black\">");
            stringBuilder.append(value);
            stringBuilder.append("</td>");
        }
        if (isForEditing) {
            stringBuilder.append(generateButton(CHANGE_ITEM_BORROWED_STATE, stringList));
            stringBuilder.append(generateButton(DELETE_ITEM_TITLE, stringList));
        }
        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }

    private StringBuilder generateButton(String deleteItemTitle, List<String> stringList) {
        return new StringBuilder().append("<td style = \"border:1px solid black\">")
                .append(WebUtils.formHTMLButton(WebUtils.generateBaseURLBuilder(deleteItemTitle, WebUtils.readUserAtr(request))
                        .addParameter(INDEX_OF_ITEM, stringList.get(0))
                        .toString(), deleteItemTitle))
                .append("</td>");
    }

    private void printHTMLLine(List<String> stringList, boolean isForEditing) {
        printWriter.printf(getLineHTMLString(stringList, isForEditing));
    }

}
