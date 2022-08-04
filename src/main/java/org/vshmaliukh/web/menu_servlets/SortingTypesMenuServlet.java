package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.GeneratedMenuForSorting;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;
import static org.vshmaliukh.web.WebUtils.MENU_ITEM_INDEX;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class SortingTypesMenuServlet extends HttpServlet {

    String title = SORTING_TYPES_MENU_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);

        if (menuItemIndex != null && !menuItemIndex.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            String classType = generatedMenu.getMenuItems().get(Integer.parseInt(menuItemIndex) - 1).getClassType().getSimpleName();

            response.sendRedirect(new URIBuilder()
                    .setPath(title)
                    .addParameter(USER_NAME, userName)
                    .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                    .addParameter(ITEM_CLASS_TYPE, classType)
                    .toString());
            //doGet(request, response);
        } else {
            response.sendRedirect(new URIBuilder()
                    .setPath(title)
                    .addParameter(USER_NAME, userName)
                    .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                    .toString());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);

        String informMessage = request.getParameter(INFORM_MESSAGE);
        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");

        webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(request, title, new GeneratedMenuForSorting()));

        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, request), "Button to " + MAIN_MENU_TITLE);

        if (informMessage != null) {
            webPageBuilder.addToBody("" +
                    " <br>\n" +
                    " <br>\n" +
                    informMessage +
                    " <br>\n");
        }
        response.getWriter().println(webPageBuilder.buildPage());
    }


}
