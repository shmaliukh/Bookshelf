package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;

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

    public static WebShelfHandler generateShelfHandler(HttpServletRequest request){
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

    public static List<List<String>> generateTable(List<Item> itemList){
        ConvertorToStringForLiterature.getTable(itemList);
        return null; // todo
    }

    public static String generateMenuItemsFormHTML(HttpServletRequest request, String servletTitle, GeneratedMenu generatedMenu) {
        StringBuilder sb = new StringBuilder();
        List<MenuItemClassType> menuItems = generatedMenu.getMenuItems();
        sb.append("<form action = \"" +
                WebUtils.generateBaseURLString(servletTitle, request) +
                "\" method = \"POST\">\n");
        for (MenuItem menuItem : menuItems) {
            sb.append("" +
                    "<input type=\"radio\" id=\"" + menuItem.getIndex() + "\"\n" +
                    "     name=\"" + MENU_ITEM_INDEX + "\" " +
                    "     value=\"" + menuItem.getIndex() + "\">\n" +
                    "    <label for=\"" + menuItem.getIndex() + "\">" + menuItem.getStr() + "</label>\n" +
                    "<br>\n"
            );
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }
}
