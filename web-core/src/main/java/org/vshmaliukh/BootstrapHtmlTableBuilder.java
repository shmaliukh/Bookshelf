package org.vshmaliukh;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.Constants.*;

public class BootstrapHtmlTableBuilder extends HtmlTableBuilder {

    public BootstrapHtmlTableBuilder(List<String> titleList, List<Map<String, String>> tableList, boolean isNeedIndex) {
        super(titleList, tableList, isNeedIndex);
    }

    public BootstrapHtmlTableBuilder(List<String> titleList, List<Map<String, String>> tableList, Map<String, String> userAtr) {
        super(titleList, tableList, userAtr);
    }

    public void buildFormattedHTMLTable() {
        setUpValuesSettings();

        tableStringBuilder.append("<table class=\"table table-striped table-sm\">");
        tableStringBuilder.append("<thead>" + buildHTMLRowString(titleList, false) + "</thead><tbody>");
        for (List<String> stringList : tableListOfLists) {
            tableStringBuilder.append(buildHTMLRowString(stringList, isForEditing));
        }
        tableStringBuilder.append("</tbody></table>");
    }

    public String buildHTMLRowString(List<String> stringList, boolean isForEditing) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<tr>");
        for (String value : stringList) {
            stringBuilder.append("<td>");
            stringBuilder.append(value);
            stringBuilder.append("</td>");
        }
        if (isForEditing) {
            stringBuilder.append(generateButtonWithIndexOfItem("Change borrowed state", stringList.get(0), CHANGE_ITEM_BORROWED_STATE_TITLE));
            stringBuilder.append(generateButtonWithIndexOfItem("Delete item", stringList.get(0), DELETE_ITEM_TITLE));
        }
        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }

    public static String generateButtonWithIndexOfItem(String label, String itemIndex, String pageToRedirect) {
        try {
            return "<td>" +
                    BootstrapHtmlBuilder.buttonWithRef(label, new URIBuilder(pageToRedirect)
                            .addParameter(INDEX_OF_ITEM, itemIndex).toString()) +
                    "</td>";
        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
        }
        return "";
    }

}
