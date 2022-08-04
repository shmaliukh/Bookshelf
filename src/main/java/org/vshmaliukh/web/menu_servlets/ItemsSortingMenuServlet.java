package org.vshmaliukh.web.menu_servlets;

import lombok.SneakyThrows;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.terminal.menus.GeneratedMenuForSorting;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.terminal.services.print_table_service.TablePrinter;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.SimpleWebApp.*;
import static org.vshmaliukh.web.WebUtils.MENU_ITEM_INDEX;
import static org.vshmaliukh.web.WebUtils.generateShelfHandler;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class ItemsSortingMenuServlet extends HttpServlet {

    final String servletTitle = ITEMS_SORTING_MENU_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(WebUtils.generateBaseURLBuilder(servletTitle, request)
                .addParameter(MENU_ITEM_INDEX, request.getParameter(MENU_ITEM_INDEX))
                .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE))
                .toString());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(servletTitle);

        String menuIndexStr = request.getParameter(MENU_ITEM_INDEX);
        String classTypeStr = request.getParameter(ITEM_CLASS_TYPE);

        if (classTypeStr != null && !classTypeStr.equals("")) {

            ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(classTypeStr);
            List sortingMenuList = handlerByName.getSortingMenuList();
            webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(request, servletTitle, sortingMenuList));

            printSortedTable(request, webPageBuilder, menuIndexStr, classTypeStr, handlerByName);
        }

        webPageBuilder.addButton(
                WebUtils.generateBaseURLString(SORTING_TYPES_MENU_TITLE, request),
                "Button to " + SORTING_TYPES_MENU_TITLE);
        WebUtils.addMessageBlock(request, webPageBuilder); // TODO

        response.getWriter().println(webPageBuilder.buildPage());
    }

    private void printSortedTable(HttpServletRequest request, WebPageBuilder webPageBuilder, String menuIndexStr, String classTypeStr, ItemHandler handlerByName) {
        if (menuIndexStr != null && !menuIndexStr.equals("")) {
            WebShelfHandler webShelfHandler = generateShelfHandler(request);
            int typeOfSorting = Integer.parseInt(menuIndexStr);
            Class<? extends Item> classType = ItemHandlerProvider.getClassByName(classTypeStr);
            List typedItemList = Utils.getItemsByType(classType, webShelfHandler.getShelf().getAllLiteratureObjects());

            List<Item> sortedList = handlerByName.getSortedItems(typeOfSorting, typedItemList);
            webPageBuilder.addToBody(webShelfHandler.generateTableOfShelfItems(sortedList, true, false));
        }
    }
}
