package org.vshmaliukh.tomcat_web_app.utils;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.menu_items.MenuItem;

import java.util.List;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.LOG_IN_TITLE;

public final class HtmlUtil {

    private HtmlUtil(){

    }

    public static String initMainMenu(Map<String, String> userAtr) {
        return "" +
                "MENU:" +
                "   <br>\n " +
                generateMainMenuItem(ADD_MENU_TITLE, "ADD item", userAtr) +
                generateMainMenuItem(EDIT_ITEMS_TITLE, "EDIT items", userAtr) +
                generateMainMenuItem(SORTING_TYPES_MENU_TITLE, "SORT items", userAtr) +
                generateMainMenuItem(LOG_IN_TITLE, "EXIT", userAtr) +
                "   <br>\n " +
                "Current state of shelf:\n " +
                "   <br>\n";
    }

    public static String generateMainMenuItem(String servletTitle, String itemTitle, Map<String, String> userAtr) {
        return "<a href=\"" + UrlUtil.generateBaseURLString(servletTitle, userAtr) + "\">" + itemTitle + "</a>\n" +
                "   <br>\n ";
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