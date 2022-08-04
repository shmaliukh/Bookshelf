package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.INFORM_MESSAGE;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class WebUtils {
    public static final String MENU_ITEM_INDEX = "menu_item_index";

    public static void redirectTo(String servletTitle, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(generateBaseURLString(servletTitle, request));
    }

    public static String generateBaseURLString(String servletTitle, HttpServletRequest request) {
        return generateBaseURLBuilder(servletTitle, request).toString();
    }

    public static URIBuilder generateBaseURLBuilder(String servletTitle, HttpServletRequest request) {
        return new URIBuilder().setPath(servletTitle)
                .addParameter(USER_NAME, request.getParameter(USER_NAME))
                .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES));
    }

    public static WebShelfHandler generateShelfHandler(HttpServletRequest request) {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        if (typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            return new WebShelfHandler(userName, typeOfWorkWithFiles);
        }
        return null;
    }

    public static String generateCurrentStateOfShelf(HttpServletRequest request) {
        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(request);
        if (webShelfHandler != null) {
            return webShelfHandler.generateTableOfShelfItems(webShelfHandler.getShelf().getAllLiteratureObjects(), false, false);
        }
        return "";
    }

    public static String generateFormHTMLStart(HttpServletRequest request, String servletTitle) {
        return "<form action = \"" +
                WebUtils.generateBaseURLString(servletTitle, request) +
                "\" method = \"POST\">\n";
    }

    public static String generateMenuItemsFormHTML(HttpServletRequest request, String servletTitle, GeneratedMenu generatedMenu) {
        StringBuilder sb = new StringBuilder();
        List<MenuItemClassType> menuItems = generatedMenu.getMenuItems();
        sb.append(generateFormHTMLStart(request, servletTitle));
        for (MenuItem menuItem : menuItems) {
            sb.append(generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }

    public static String generateMenuItemsFormHTML(HttpServletRequest request, String servletTitle, List<MenuItemForSorting> menuItemForSortingList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action = \"" +
                WebUtils.generateBaseURLBuilder(servletTitle, request)
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE))
                        .toString() +
                "\" method = \"POST\">\n");
        for (MenuItemForSorting menuItem : menuItemForSortingList) {
            sb.append(generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }

    private static String generateMenuItemRadio(MenuItem menuItem) {
        return "" +
                "<input type=\"radio\" id=\"" + menuItem.getIndex() + "\"\n" +
                "     name=\"" + MENU_ITEM_INDEX + "\" " +
                "     value=\"" + menuItem.getIndex() + "\">\n" +
                "    <label for=\"" + menuItem.getIndex() + "\">" + menuItem.getStr() + "</label>\n" +
                "<br>\n";
    }

    public static void addMessageBlock(HttpServletRequest request, WebPageBuilder webPageBuilder) {
        String informMessage = request.getParameter(INFORM_MESSAGE);
        if (informMessage != null) {
            webPageBuilder.addToBody("" +
                    " <br>\n" +
                    " <br>\n" +
                    informMessage +
                    " <br>\n");
        }
    }
}
