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

    final String servletTitle = SORTING_TYPES_MENU_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);

        if (menuItemIndex != null && !menuItemIndex.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            String classType = generatedMenu.getMenuItems().get(Integer.parseInt(menuItemIndex) - 1).getClassType().getSimpleName();

            response.sendRedirect(
                    WebUtils.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, request)
                    .addParameter(ITEM_CLASS_TYPE, classType)
                    .toString());
        } else {
            WebUtils.redirectTo(servletTitle, response, request);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(servletTitle);

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
        webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(request, servletTitle, new GeneratedMenuForSorting()));
        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, request), MAIN_MENU_TITLE);
        WebUtils.addMessageBlock(request, webPageBuilder);

        response.getWriter().println(webPageBuilder.buildPage());
    }
}
