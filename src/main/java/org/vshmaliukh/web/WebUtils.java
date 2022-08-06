package org.vshmaliukh.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.console_terminal.menus.GeneratedMenu;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.console_terminal.services.print_table_service.ConvertorToStringForLiterature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.web.BookShelfWebApp.USER_PARAMETER_LIST;

import static org.vshmaliukh.web.servlets.EditItemsServlet.INDEX_OF_ITEM;
import static org.vshmaliukh.web.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.servlets.LogInServlet.USER_NAME;

@Slf4j
public final class WebUtils {

    public static final Random RANDOM = new Random();
    public static final String MENU_ITEM_INDEX = "menu_item_index";
    public static final String INFORM_MESSAGE = "inform_message";

    private WebUtils() {
    }

    public static void logServletErr(String servletTitle, Exception e) {
        log.error("[Servlet] '" + servletTitle + "' got err. Exception: ", e);
    }

    public static void redirectTo(String servletTitle, HttpServletResponse response, Map<String, String> userAtr) {
        try {
            response.sendRedirect(generateBaseURLString(servletTitle, userAtr));
        } catch (IOException ioe) {
            logServletErr(servletTitle, ioe);
        }
    }

    public static String generateBaseURLString(String servletTitle, Map<String, String> userAtr) {
        return generateBaseURLBuilder(servletTitle, userAtr).toString();
    }

    public static URIBuilder generateBaseURLBuilder(String servletTitle, Map<String, String> userAtr) {
        URIBuilder uriBuilder = new URIBuilder().setPath(servletTitle);
        for (String parameter : USER_PARAMETER_LIST) {
            uriBuilder.addParameter(parameter, userAtr.get(parameter));
        }
        return uriBuilder;
    }

    // TODO write test
    public static WebShelfHandler generateShelfHandler(Map<String, String> userAtr) {
        String userName = userAtr.get(USER_NAME);
        String typeOfWorkWithFilesStr = userAtr.get(TYPE_OF_WORK_WITH_FILES);

        if (typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            return new WebShelfHandler(userName, typeOfWorkWithFiles);
        }
        return null;
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
                WebUtils.generateBaseURLString(servletTitle, userAtr) +
                "\" method = \"POST\">\n";
    }

    public static String generateMenuItemsFormHTML(Map<String, String> userAtr, String servletTitle, GeneratedMenu generatedMenu) {
        StringBuilder sb = new StringBuilder();
        List<? extends MenuItem> menuItems = generatedMenu.getMenuItems();
        sb.append(generateFormHTMLStart(userAtr, servletTitle));
        for (MenuItem menuItem : menuItems) {
            sb.append(generateMenuItemRadio(menuItem));
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
            sb.append(generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }

    public static String generateMenuItemRadio(MenuItem menuItem) {
        return "" +
                "<input type=\"radio\" id=\"" + menuItem.getIndex() + "\"\n" +
                "     name=\"" + MENU_ITEM_INDEX + "\" " +
                "     value=\"" + menuItem.getIndex() + "\">\n" +
                "    <label for=\"" + menuItem.getIndex() + "\">" + menuItem.getStr() + "</label>\n" +
                "<br>\n";
    }

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, List<String> titleList, boolean isNeedIndex) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print <br>\n";
        } else {
            HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder(titleList, ConvertorToStringForLiterature.getTable(shelfLiteratureObjects), isNeedIndex);
            return htmlTableBuilder.generateHTMLTableStr();
        }
    }

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, boolean isNeedIndex) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print <br>\n";
        } else {
            HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder(ConvertorToStringForLiterature.getTable(shelfLiteratureObjects), isNeedIndex);
            return htmlTableBuilder.generateHTMLTableStr();
        }
    }

    public static String generateCurrentStateOfShelf(Map<String, String> userAtr, List<String> titleList) {
        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            return generateTableOfShelfItems(webShelfHandler.getShelf().getAllLiteratureObjects(), titleList, true);
        }
        return "";
    }

    public static String generateTableForEditingItems(Map<String, String> userAtr, List<String> titleList) {
        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            if(allLiteratureObjects.isEmpty()){
                return "No available literature IN shelf to edit <br>\n ";
            }
            else {
                HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder(titleList, ConvertorToStringForLiterature.getTable(allLiteratureObjects), userAtr);
                return htmlTableBuilder.generateHTMLTableStr();
            }
        }
        return "";
    }

    public static Map<String, String> readUserAtr(HttpServletRequest request) {
        return USER_PARAMETER_LIST.stream().collect(Collectors.toMap(parameterName -> parameterName, request::getParameter));
    }

    public static Map<String, String> readMapOfItemFields(HttpServletRequest request) {
        Map<String, String> mapOfItemFields = Collections.list(request.getParameterNames())
                .stream()
                .collect(Collectors.toMap(parameterName -> parameterName, request::getParameter));
        USER_PARAMETER_LIST.forEach(mapOfItemFields::remove);
        return mapOfItemFields;
    }

    public static String generateButtonWithIndexOfItem(String servletTitle, String itemIndex, Map<String, String> userAtr) {
        return "<td style = \"border:1px solid black\">" +
                WebUtils.formHTMLButton(WebUtils.generateBaseURLBuilder(servletTitle, userAtr)
                        .addParameter(INDEX_OF_ITEM, itemIndex)
                        .toString(), servletTitle) +
                "</td>";
    }
}