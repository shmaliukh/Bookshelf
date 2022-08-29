package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.services.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.utils.HtmlUtil.generateMenuItemsFormHTML;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.*;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;

public class ItemsSortingMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        UrlUtil.redirectTo(ITEMS_SORTING_MENU_TITLE, response,
                UrlUtil.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, WebUtils.readUserAtr(request))
                        .addParameter(MENU_ITEM_INDEX, request.getParameter(MENU_ITEM_INDEX))
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE)));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(ITEMS_SORTING_MENU_TITLE);
        Map<String, String> userAtr = readUserAtr(request);

        String menuIndexStr = request.getParameter(MENU_ITEM_INDEX);
        String classTypeStr = request.getParameter(ITEM_CLASS_TYPE);

        if (classTypeStr != null && !classTypeStr.equals("")) {

            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(classTypeStr);

            webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
            webPageBuilder.addToBody(initMenu(request, handlerByName));

            printSortedTable(userAtr, webPageBuilder, menuIndexStr, classTypeStr, handlerByName);
        }

        webPageBuilder.addButton(UrlUtil.generateBaseURLString(SORTING_TYPES_MENU_TITLE, userAtr), SORTING_TYPES_MENU_TITLE);

        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ITEMS_SORTING_MENU_TITLE, ioe);
        }
    }

    private String initMenu(HttpServletRequest request, ItemHandler<?> handlerByName) {
        return generateMenuItemsFormHTML(UrlUtil.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, readUserAtr(request))
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE)),
                handlerByName.getSortingMenuList());
    }

    private <T extends Item> void printSortedTable(Map<String, String> userAtr, WebPageBuilder webPageBuilder, String menuIndexStr, String classTypeStr, ItemHandler<T> handlerByName) {
        Class classType = ItemHandlerProvider.getClassByName(classTypeStr);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        List<T> sortedItemsByClass = new ArrayList<>();
        if (webShelfHandler != null) {
            sortedItemsByClass = webShelfHandler.getSortedItemsByClass(classType);
        }
        if (menuIndexStr != null && !menuIndexStr.equals("")) {
            int typeOfSorting = Integer.parseInt(menuIndexStr);
            List<T> sortedList = handlerByName.getSortedItems(typeOfSorting, sortedItemsByClass);
            webPageBuilder.addToBody(generateTableOfShelfItems(sortedList, true));
        } else {
            webPageBuilder.addToBody(generateTableOfShelfItems(sortedItemsByClass, true));
        }
    }
}
