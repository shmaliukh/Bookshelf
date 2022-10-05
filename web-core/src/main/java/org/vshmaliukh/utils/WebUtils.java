package org.vshmaliukh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.vshmaliukh.BootstrapHtmlTableBuilder;
import org.vshmaliukh.HtmlTableBuilder;
import org.vshmaliukh.WebUI;
import org.vshmaliukh.services.ConvertorToStringForItems;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.menu_items.MenuItem;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemTitles;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.vshmaliukh.Constants.*;

@Slf4j
public final class WebUtils {

    public static final Random RANDOM = new Random();

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

    public static SaveReadShelfHandler generateShelfHandler(String userName, int typeOfWorkWithFiles) {
        if(StringUtils.isNotBlank(userName)){
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
            HtmlTableBuilder htmlTableBuilder = new BootstrapHtmlTableBuilder(titleList, ConvertorToStringForItems.getTable(shelfLiteratureObjects), isNeedIndex);
            return htmlTableBuilder.generateHTMLTableStr();
        }
    }

    public static String generateTableOfShelfItems(List<? extends Item> shelfLiteratureObjects, boolean isNeedIndex) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print <br>\n";
        } else {
            HtmlTableBuilder htmlTableBuilder = new BootstrapHtmlTableBuilder(ItemTitles.TITLE_LIST, ConvertorToStringForItems.getTable(shelfLiteratureObjects), isNeedIndex);
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
                HtmlTableBuilder htmlTableBuilder = new BootstrapHtmlTableBuilder(ItemTitles.TITLE_LIST, ConvertorToStringForItems.getTable(allLiteratureObjects), userAtr);
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

    public static Map<String, String> readMapOfItemFields(Map map) {
        Map<String, String> mapOfItemFields = new HashMap<>(map);
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
