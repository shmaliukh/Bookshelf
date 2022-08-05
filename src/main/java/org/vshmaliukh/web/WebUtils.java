package org.vshmaliukh.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.INFORM_MESSAGE;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

@Slf4j
public final class WebUtils {

    private WebUtils(){}

    public static final Random RANDOM = new Random();

    public static final String MENU_ITEM_INDEX = "menu_item_index";

    public static void logServletErr(String servletTitle, Exception e) {
        log.error("[Servlet] '" + servletTitle + "' got err. Exception: " + e.getMessage());
    }

    public static void redirectTo(String servletTitle, HttpServletResponse response, HttpServletRequest request) {
        try {
            response.sendRedirect(generateBaseURLString(servletTitle, request));
        } catch (IOException ioe) {
            logServletErr(servletTitle, ioe);
        }
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



    public static String generateFormHTMLStart(HttpServletRequest request, String servletTitle) {
        return "<form action = \"" +
                WebUtils.generateBaseURLString(servletTitle, request) +
                "\" method = \"POST\">\n";
    }

    public static String generateMenuItemsFormHTML(HttpServletRequest request, String servletTitle, GeneratedMenu generatedMenu) {
        StringBuilder sb = new StringBuilder();
        List<? extends MenuItem> menuItems = generatedMenu.getMenuItems();
        sb.append(generateFormHTMLStart(request, servletTitle));
        for (MenuItem menuItem : menuItems) {
            sb.append(generateMenuItemRadio(menuItem));
        }
        sb.append("<input type = \"submit\" value = \"Submit\" />\n" + "</form>");
        return sb.toString();
    }

    public static String generateMenuItemsFormHTML(HttpServletRequest request, String servletTitle, List<? extends MenuItem> menuItemForSortingList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action = \"")
                .append(WebUtils.generateBaseURLBuilder(servletTitle, request)
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

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, boolean isNeedIndex, boolean isForEditing) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print";
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // TODO remove baos
            PrintWriter printWriter = new PrintWriter(baos, true);
            HtmlTablePrinter htmlTablePrinter = new HtmlTablePrinter(printWriter, ConvertorToStringForLiterature.getTable(shelfLiteratureObjects), isNeedIndex, isForEditing);
            htmlTablePrinter.setTitleList(Arrays.asList(ItemTitles.NAME, ItemTitles.PAGES, ItemTitles.BORROWED, ItemTitles.AUTHOR,  ItemTitles.DATE, ItemTitles.PUBLISHER));
            htmlTablePrinter.print();
            return baos.toString();
        }
    }

    public static String generateCurrentStateOfShelf(HttpServletRequest request) {
        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(request);
        if (webShelfHandler != null) {
            return generateTableOfShelfItems(webShelfHandler.getShelf().getAllLiteratureObjects(), false, false);
        }
        return "";
    }
}
