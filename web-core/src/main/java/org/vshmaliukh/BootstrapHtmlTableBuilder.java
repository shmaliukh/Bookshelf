package org.vshmaliukh;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.Constants.*;

@Slf4j
public class BootstrapHtmlTableBuilder extends HtmlTableBuilder {

    public BootstrapHtmlTableBuilder(List<String> titleList, List<Map<String, String>> tableList, boolean isNeedIndex) {
        super(titleList, tableList, isNeedIndex);
    }

    public BootstrapHtmlTableBuilder(List<String> titleList, List<Map<String, String>> tableList, Map<String, String> userAtr) {
        super(titleList, tableList, userAtr);
    }

    @Override
    public void buildFormattedHTMLTable() {
        setUpValuesSettings();

        tableStringBuilder.append("<table class=\"table table-striped table-sm\">");
        tableStringBuilder.append("<thead>").append(buildHTMLRowString(titleList, false)).append("</thead><tbody>");
        for (List<String> stringList : tableListOfLists) {
            tableStringBuilder.append(buildHTMLRowString(stringList, isForEditing));
        }
        tableStringBuilder.append("</tbody></table>");
    }

    @Override
    public String buildHTMLRowString(List<String> stringList, boolean isForEditing) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<tr>");
        for (String value : stringList) {
            stringBuilder.append("<td>");
            stringBuilder.append(value);
            stringBuilder.append("</td>");
        }
        if (isForEditing) {
            stringBuilder.append(generateSellButtonWithIndexOfItem("Change borrowed state", stringList.get(0), CHANGE_ITEM_BORROWED_STATE_TITLE));
            stringBuilder.append(generateSellButtonWithIndexOfItem("Delete item", stringList.get(0), DELETE_ITEM_TITLE));
        }
        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }

    static String generateSellButtonWithIndexOfItem(String label, String itemIndex, String pageToRedirect) {
        try {
            return "<td>" +
                    BootstrapHtmlBuilder.buttonWithRefAndCustomClassStyle(label, new URIBuilder(pageToRedirect)
                            .addParameter(INDEX_OF_ITEM, itemIndex).toString(), "w-100 btn btn-lg btn-outline-primary") +
                    "</td>";
        } catch (URISyntaxException urie) {
            log.error("[BootstrapHtmlTableBuilder] error: URISyntaxException", urie);
        }
        return "";
    }

}
