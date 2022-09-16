package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.Constants.ITEM_CLASS_TYPE;
import static org.vshmaliukh.utils.HtmlUtil.generateMenuItemsFormHTML;
import static org.vshmaliukh.utils.WebUtils.*;

public class ItemsSortingMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        UrlUtil.redirectTo(Constants.ITEMS_SORTING_MENU_TITLE, response,
                UrlUtil.generateBaseURLBuilder(Constants.ITEMS_SORTING_MENU_TITLE, readUserAtr(request))
                        .addParameter(Constants.MENU_ITEM_INDEX, request.getParameter(Constants.MENU_ITEM_INDEX))
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE)));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.ITEMS_SORTING_MENU_TITLE);
        Map<String, String> userAtr = readUserAtr(request);

        String menuIndexStr = request.getParameter(Constants.MENU_ITEM_INDEX);
        String classTypeStr = request.getParameter(ITEM_CLASS_TYPE);

        if (classTypeStr != null && !classTypeStr.equals("")) {

            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(classTypeStr);

            webPageBuilder.addToBody(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");
            webPageBuilder.addToBody(initMenu(request, handlerByName));

            printSortedTable(userAtr, webPageBuilder, menuIndexStr, classTypeStr, handlerByName);
        }

        webPageBuilder.addButton(UrlUtil.generateBaseURLString(Constants.SORTING_TYPES_MENU_TITLE, userAtr), Constants.SORTING_TYPES_MENU_TITLE);

        webPageBuilder.addMessageBlock(request.getParameter(Constants.INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            logServletErr(Constants.ITEMS_SORTING_MENU_TITLE, ioe);
        }
    }

    private String initMenu(HttpServletRequest request, ItemHandler<?> handlerByName) {
        return generateMenuItemsFormHTML(UrlUtil.generateBaseURLBuilder(Constants.ITEMS_SORTING_MENU_TITLE, readUserAtr(request))
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
