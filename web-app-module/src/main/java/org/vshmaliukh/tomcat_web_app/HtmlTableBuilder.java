package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.print_table_service.AbstractTableHandler;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import java.util.List;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.CHANGE_ITEM_BORROWED_STATE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.DELETE_ITEM_TITLE;

public class HtmlTableBuilder extends AbstractTableHandler {

    private final StringBuilder tableStringBuilder = new StringBuilder();
    private final boolean isForEditing;
    private final Map<String, String> userAtr;

    public HtmlTableBuilder(List<String> titleList, List<Map<String, String>> tableList, boolean isNeedIndex) {
        super(tableList, isNeedIndex);
        super.titleList = titleList;
        this.isForEditing = false;
        this.userAtr = null;
    }

    public HtmlTableBuilder(List<String> titleList, List<Map<String, String>> tableList,  Map<String, String> userAtr) {
        super(tableList, true);
        this.isForEditing = true;
        super.titleList = titleList;
        this.userAtr = userAtr;
    }

    public String generateHTMLTableStr() {
        buildFormattedHTMLTable();
        return tableStringBuilder.toString();
    }

    @Override
    protected void setUpValuesSettings() {
        initTitles(bufferTableListOfMaps);
        initTable(bufferTableListOfMaps);
        if (isNeedIndex) {
            addIndexBeforeLines();
        }
    }

    public void buildFormattedHTMLTable() {
        setUpValuesSettings();

        tableStringBuilder.append("<table style = \"border:1px solid black\">");
        tableStringBuilder.append(buildHTMLRowString(titleList, false));
        for (List<String> stringList : tableListOfLists) {
            tableStringBuilder.append(buildHTMLRowString(stringList, isForEditing));
        }
        tableStringBuilder.append("</table>");
    }

    public String buildHTMLRowString(List<String> stringList, boolean isForEditing) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<tr style = \"border:1px solid black\">");
        for (String value : stringList) {
            stringBuilder.append("<td style = \"border:1px solid black\">");
            stringBuilder.append(value);
            stringBuilder.append("</td>");
        }
        if (isForEditing) {
            stringBuilder.append(WebUtils.generateButtonWithIndexOfItem(CHANGE_ITEM_BORROWED_STATE, stringList.get(0), userAtr));
            stringBuilder.append(WebUtils.generateButtonWithIndexOfItem(DELETE_ITEM_TITLE, stringList.get(0), userAtr));
        }
        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }
}
