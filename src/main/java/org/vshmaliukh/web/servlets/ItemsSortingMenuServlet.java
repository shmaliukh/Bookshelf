package org.vshmaliukh.web.servlets;

import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.console_terminal.services.Utils;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.console_terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.WebUtils.*;
import static org.vshmaliukh.web.servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class ItemsSortingMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(WebUtils.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, WebUtils.readUserAtr(request))
                    .addParameter(MENU_ITEM_INDEX, request.getParameter(MENU_ITEM_INDEX))
                    .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE))
                    .toString());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ITEMS_SORTING_MENU_TITLE, ioe);
        }
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

    private void printSortedTable(Map<String, String> userAtr, WebPageBuilder webPageBuilder, String menuIndexStr, String classTypeStr, ItemHandler<? extends Item> handlerByName) {
        Class<? extends Item> classType = ItemHandlerProvider.getClassByName(classTypeStr);
        WebShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        List typedItemList = null;
        if (webShelfHandler != null) {
            typedItemList = Utils.getItemsByType(classType, webShelfHandler.getShelf().getAllLiteratureObjects());
        }
        // TODO Raw use of parameterized class 'List'
        if (menuIndexStr != null && !menuIndexStr.equals("")) {

            int typeOfSorting = Integer.parseInt(menuIndexStr);
            List<? extends Item> sortedList = handlerByName.getSortedItems(typeOfSorting, typedItemList);
            webPageBuilder.addToBody(generateTableOfShelfItems(sortedList, true));
        }
        else {
            webPageBuilder.addToBody(generateTableOfShelfItems(typedItemList, true));
        }
    }
}
