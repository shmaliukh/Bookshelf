package org.vshmaliukh.web.menu_servlets;

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

import static org.vshmaliukh.console_terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.BookShelfWebApp.ITEMS_SORTING_MENU_TITLE;
import static org.vshmaliukh.web.BookShelfWebApp.SORTING_TYPES_MENU_TITLE;
import static org.vshmaliukh.web.WebUtils.*;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

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
        List<String> userAtr = readUserAtr(request);


        String menuIndexStr = request.getParameter(MENU_ITEM_INDEX);
        String classTypeStr = request.getParameter(ITEM_CLASS_TYPE);

        if (classTypeStr != null && !classTypeStr.equals("")) {

            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(classTypeStr);

            webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
            webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(request, ITEMS_SORTING_MENU_TITLE, handlerByName.getSortingMenuList()));

            printSortedTable(userAtr, webPageBuilder, menuIndexStr, classTypeStr, handlerByName);
        }

        webPageBuilder.addButton(WebUtils.generateBaseURLString(SORTING_TYPES_MENU_TITLE, userAtr), SORTING_TYPES_MENU_TITLE);
        WebUtils.addMessageBlock(request, webPageBuilder); // TODO

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ITEMS_SORTING_MENU_TITLE, ioe);
        }
    }

    private void printSortedTable(List<String> userAtr, WebPageBuilder webPageBuilder, String menuIndexStr, String classTypeStr, ItemHandler<? extends Item> handlerByName) {
        if (menuIndexStr != null && !menuIndexStr.equals("")) {
            WebShelfHandler webShelfHandler = generateShelfHandler(userAtr);

            int typeOfSorting = Integer.parseInt(menuIndexStr);
            Class<? extends Item> classType = ItemHandlerProvider.getClassByName(classTypeStr);
            List typedItemList = null;
            if (webShelfHandler != null) {
                typedItemList = Utils.getItemsByType(classType, webShelfHandler.getShelf().getAllLiteratureObjects());
            }
            // TODO Raw use of parameterized class 'List'
            List<? extends Item> sortedList = handlerByName.getSortedItems(typeOfSorting, typedItemList);
            webPageBuilder.addToBody(generateTableOfShelfItems(sortedList, true));
        }
    }
}
