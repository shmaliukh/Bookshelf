package org.vshmaliukh;

import org.vshmaliukh.print_table_service.BaseTableHandler;
import org.vshmaliukh.utils.WebUtils;

import java.util.List;
import java.util.Map;

public class HtmlTableBuilder extends BaseTableHandler {

    protected final StringBuilder tableStringBuilder = new StringBuilder();
    protected final boolean isForEditing;
    protected final Map<String, String> userAtr;

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
        initTitles();
        initTable();
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
            stringBuilder.append(WebUtils.generateButtonWithIndexOfItem(Constants.CHANGE_ITEM_BORROWED_STATE_TITLE, stringList.get(0), userAtr));
            stringBuilder.append(WebUtils.generateButtonWithIndexOfItem(Constants.DELETE_ITEM_TITLE, stringList.get(0), userAtr));
        }
        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }
}
