package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.ItemUtils;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.WebUserHandler;
import org.vshmaliukh.tomcat_web_app.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.services.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.WebUtils.*;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;

public class ItemsSortingMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        WebUtils.redirectTo(ITEMS_SORTING_MENU_TITLE, response,
                WebUtils.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, WebUtils.readUserAtr(request))
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

        webPageBuilder.addButton(WebUtils.generateBaseURLString(SORTING_TYPES_MENU_TITLE, userAtr), SORTING_TYPES_MENU_TITLE);

        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ITEMS_SORTING_MENU_TITLE, ioe);
        }
    }

    private String initMenu(HttpServletRequest request, ItemHandler<?> handlerByName) {
        return generateMenuItemsFormHTML(generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, readUserAtr(request))
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE)),
                handlerByName.getSortingMenuList());
    }

    private <T extends Item> void printSortedTable(Map<String, String> userAtr, WebPageBuilder webPageBuilder, String menuIndexStr, String classTypeStr, ItemHandler<T> handlerByName) {
        Class<T> classType = (Class<T>) ItemHandlerProvider.getClassByName(classTypeStr);
        WebUserHandler webShelfHandler = generateShelfHandler(userAtr);
        List<T> typedItemList = new ArrayList<>();
        if (webShelfHandler != null) {
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            typedItemList = ItemUtils.getItemsByType(classType, allLiteratureObjects);
        }
        if (menuIndexStr != null && !menuIndexStr.equals("")) {
            int typeOfSorting = Integer.parseInt(menuIndexStr);
            List<T> sortedList = handlerByName.getSortedItems(typeOfSorting, typedItemList);
            webPageBuilder.addToBody(generateTableOfShelfItems(sortedList, true));
        } else {
            webPageBuilder.addToBody(generateTableOfShelfItems(typedItemList, true));
        }
    }
}
