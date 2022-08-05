package org.vshmaliukh.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.console_terminal.menus.GeneratedMenu;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.console_terminal.services.print_table_service.ConvertorToStringForLiterature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.web.BookShelfWebApp.INFORM_MESSAGE;
import static org.vshmaliukh.web.BookShelfWebApp.USER_PARAMETER_LIST;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

@Slf4j
public final class WebUtils {

    public static final Random RANDOM = new Random();
    public static final String MENU_ITEM_INDEX = "menu_item_index";

    private WebUtils() {
    }

    public static void logServletErr(String servletTitle, Exception e) {
        log.error("[Servlet] '" + servletTitle + "' got err. Exception: " + e.getMessage());
    }

    public static void redirectTo(String servletTitle, HttpServletResponse response, List<String> userAtr) {
        try {
            response.sendRedirect(generateBaseURLString(servletTitle, userAtr));
        } catch (IOException ioe) {
            logServletErr(servletTitle, ioe);
        }
    }

    public static String generateBaseURLString(String servletTitle, List<String> userAtr) {
        return generateBaseURLBuilder(servletTitle, userAtr).toString();
    }

    public static URIBuilder generateBaseURLBuilder(String servletTitle, List<String> userAtr) {
        return new URIBuilder().setPath(servletTitle)
                .addParameter(USER_NAME, userAtr.get(0))
                .addParameter(TYPE_OF_WORK_WITH_FILES, userAtr.get(1));
    }

    //public static WebShelfHandler generateShelfHandler(HttpServletRequest request) {
    //    String userName = request.getParameter(USER_NAME);
    //    String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
    //    if (typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
    //        int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
    //        return new WebShelfHandler(userName, typeOfWorkWithFiles);
    //    }
    //    return null;
    //}

    public static WebShelfHandler generateShelfHandler(List<String> userAtr) {
        String userName = userAtr.get(0);
        String typeOfWorkWithFilesStr = userAtr.get(1);

        if (typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            return new WebShelfHandler(userName, typeOfWorkWithFiles);
        }
        return null;
    }

    public static String formHTMLButton(String reference, String label) {
        return new StringBuilder()
                //.append(" <br> \n")
                .append("<button ")
                .append("onclick=\"window.location.href='")
                .append(reference)
                .append("';\"> ")
                .append(label)
                .append("</button> \n")
                .toString();
    }


    public static String generateFormHTMLStart(List<String> userAtr, String servletTitle) {
        return "<form action = \"" +
                WebUtils.generateBaseURLString(servletTitle, userAtr) +
                "\" method = \"POST\">\n";
    }

    public static String generateMenuItemsFormHTML(List<String> userAtr, String servletTitle, GeneratedMenu generatedMenu) {
        StringBuilder sb = new StringBuilder();
        List<? extends MenuItem> menuItems = generatedMenu.getMenuItems();
        sb.append(generateFormHTMLStart(userAtr, servletTitle));
        for (MenuItem menuItem : menuItems) {
            sb.append(generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }

    public static String generateMenuItemsFormHTML(HttpServletRequest request, String servletTitle, List<? extends MenuItem> menuItemForSortingList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action = \"")
                .append(WebUtils.generateBaseURLBuilder(servletTitle, WebUtils.readUserAtr(request))
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE)).toString())
                .append("\" method = \"POST\">\n");
        for (MenuItem menuItem : menuItemForSortingList) {
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
                    "<br>\n" +
                    "<br>\n" +
                    informMessage +
                    "<br>\n");
        }
    }

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, List<String> titleList, boolean isNeedIndex) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print";
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // TODO remove baos
            PrintWriter printWriter = new PrintWriter(baos, true);
            HtmlTablePrinter htmlTablePrinter = new HtmlTablePrinter(printWriter, titleList, ConvertorToStringForLiterature.getTable(shelfLiteratureObjects), isNeedIndex);
            htmlTablePrinter.print();
            return baos.toString();
        }
    }

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, boolean isNeedIndex) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print";
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // TODO remove baos
            PrintWriter printWriter = new PrintWriter(baos, true);
            HtmlTablePrinter htmlTablePrinter = new HtmlTablePrinter(printWriter, ConvertorToStringForLiterature.getTable(shelfLiteratureObjects), isNeedIndex);
            htmlTablePrinter.print();
            return baos.toString();
        }
    }

    public static String generateCurrentStateOfShelf(List<String> userAtr, List<String> titleList) {
        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            return generateTableOfShelfItems(webShelfHandler.getShelf().getAllLiteratureObjects(), titleList, true);
        }
        return "";
    }

    public static void printTableForEditingItems(List<String> userAtr, HttpServletRequest request, List<String> titleList, PrintWriter printWriter) {
        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            HtmlTablePrinter htmlTablePrinter = new HtmlTablePrinter(printWriter, titleList, ConvertorToStringForLiterature.getTable(allLiteratureObjects), true, true, request);
            htmlTablePrinter.print();
        }
    }

    public static List<String> readUserAtr(HttpServletRequest request) {
        return USER_PARAMETER_LIST.stream()
                .map(o -> o!= null ? request.getParameter(o) : "")
                .collect(Collectors.toList());
    }

    public static Map<String, String> readMapOfItemFields(HttpServletRequest request){
        Map<String, String> mapOfItemFields = Collections.list(request.getParameterNames())
                .stream()
                .collect(Collectors.toMap(parameterName -> parameterName, request::getParameter));
        USER_PARAMETER_LIST.forEach(mapOfItemFields::remove);
        return mapOfItemFields;
    }
}
