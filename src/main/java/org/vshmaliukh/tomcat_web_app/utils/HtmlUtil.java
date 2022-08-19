package org.vshmaliukh.tomcat_web_app.utils;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.menu_items.MenuItem;

import java.util.List;
import java.util.Map;

public final class HtmlUtil {

    private HtmlUtil(){

    }

    public static String formHTMLButton(String referenceStr, String label) {
        return "" +
                "<button " +
                "onclick=\"window.location.href='" +
                referenceStr +
                "';\"> " +
                label +
                "</button> \n";
    }

    public static String generateFormHTMLStart(Map<String, String> userAtr, String servletTitle) {
        return "<form action = \"" +
                UrlUtil.generateBaseURLString(servletTitle, userAtr) +
                "\" method = \"POST\">\n";
    }

    public static String generateMenuItemsFormHTML(Map<String, String> userAtr, String servletTitle, GeneratedMenu generatedMenu) {
        StringBuilder sb = new StringBuilder();
        List<? extends MenuItem> menuItems = generatedMenu.getMenuItems();
        sb.append(generateFormHTMLStart(userAtr, servletTitle));
        for (MenuItem menuItem : menuItems) {
            sb.append(WebUtils.generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }

    public static String generateMenuItemsFormHTML(URIBuilder uriBuilder, List<? extends MenuItem> menuItemForSortingList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action = \"")
                .append(uriBuilder.toString())
                .append("\" method = \"POST\">\n");
        for (MenuItem menuItem : menuItemForSortingList) {
            sb.append(WebUtils.generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }
}