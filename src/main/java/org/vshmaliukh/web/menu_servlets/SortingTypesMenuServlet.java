package org.vshmaliukh.web.menu_servlets;

import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.GeneratedMenuForSorting;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.SimpleWebApp.*;
import static org.vshmaliukh.web.WebUtils.MENU_ITEM_INDEX;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class SortingTypesMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);

        if (menuItemIndex != null && !menuItemIndex.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();

                response.sendRedirect(WebUtils.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, request)
                        .addParameter(ITEM_CLASS_TYPE, classType)
                        .toString());
            } catch (Exception e) {
                WebUtils.logServletErr(SORTING_TYPES_MENU_TITLE, e);
            }
        } else {
            WebUtils.redirectTo(SORTING_TYPES_MENU_TITLE, response, request);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(SORTING_TYPES_MENU_TITLE);

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
        webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(request, SORTING_TYPES_MENU_TITLE, new GeneratedMenuForSorting()));
        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, request), MAIN_MENU_TITLE);
        WebUtils.addMessageBlock(request, webPageBuilder);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(SORTING_TYPES_MENU_TITLE, ioe);
        }
    }
}
