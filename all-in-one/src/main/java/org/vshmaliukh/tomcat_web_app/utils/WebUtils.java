package org.vshmaliukh.tomcat_web_app.utils;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForItems;
import org.vshmaliukh.tomcat_web_app.HtmlTableBuilder;
import org.vshmaliukh.tomcat_web_app.WebUI;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.TITLE_LIST;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.USER_PARAMETER_LIST;

import static org.vshmaliukh.tomcat_web_app.servlets.EditItemsServlet.INDEX_OF_ITEM;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

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

    public static SaveReadShelfHandler generateShelfHandler(Map<String, String> userAtr) {
        String userName = userAtr.get(USER_NAME);
        String typeOfWorkWithFilesStr = userAtr.get(TYPE_OF_WORK_WITH_FILES);

        if (typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            WebUI webUI = new WebUI(userName, typeOfWorkWithFiles);

            SaveReadShelfHandler shelfHandler = webUI.getShelfHandler();
            shelfHandler.readShelfItems();
            return shelfHandler;
        }
        return null;
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
            HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder(titleList, ConvertorToStringForItems.getTable(shelfLiteratureObjects), isNeedIndex);
            return htmlTableBuilder.generateHTMLTableStr();
        }
    }

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, boolean isNeedIndex) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print <br>\n";
        } else {
            HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder(TITLE_LIST, ConvertorToStringForItems.getTable(shelfLiteratureObjects), isNeedIndex);
            return htmlTableBuilder.generateHTMLTableStr();
        }
    }

    public static String generateCurrentStateOfShelf(Map<String, String> userAtr, List<String> titleList) {
        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            return generateTableOfShelfItems(webShelfHandler.getShelf().getAllLiteratureObjects(), titleList, true);
        }
        return "No available literature items to display <br>\n";
    }

    public static String generateTableForEditingItems(Map<String, String> userAtr) {
        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            if (!allLiteratureObjects.isEmpty()) {
                HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder(TITLE_LIST, ConvertorToStringForItems.getTable(allLiteratureObjects), userAtr);
                return htmlTableBuilder.generateHTMLTableStr();
            }
        }
        return "No available literature items to edit <br>\n";
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
                HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLBuilder(servletTitle, userAtr)
                        .addParameter(INDEX_OF_ITEM, itemIndex)
                        .toString(), servletTitle) +
                "</td>";
    }
}
