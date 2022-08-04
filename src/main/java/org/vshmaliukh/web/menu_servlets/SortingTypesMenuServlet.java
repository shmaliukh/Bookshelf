package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.GeneratedMenuForSorting;
import org.vshmaliukh.terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.web.WebPageBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class SortingTypesMenuServlet extends HttpServlet {

    public static final String SORTING_MENU_ITEM_INDEX = "sorting_menu_item_index";

    String title = SORTING_TYPES_MENU_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(SORTING_MENU_ITEM_INDEX);

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
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String informMessage = request.getParameter(INFORM_MESSAGE);

        GeneratedMenuForSorting generatedMenu = new GeneratedMenuForSorting();
        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");

        initPageFormStart(request, webPageBuilder);
        initPageMenuItems(webPageBuilder, generatedMenu.getMenuItems());
        initPageFormEnd(webPageBuilder);


        webPageBuilder.addButton(new URIBuilder()
                        .setPath(MAIN_MENU_TITLE)
                        .addParameter(USER_NAME, userName)
                        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                        .toString(),
                "Button to " + MAIN_MENU_TITLE);

        if (informMessage != null) {
            webPageBuilder.addToBody("" +
                    " <br>\n" +
                    " <br>\n" +
                    informMessage +
                    " <br>\n");
        }
        writer.println(webPageBuilder.buildPage());
    }

    private void initPageFormEnd(WebPageBuilder webPageBuilder) {
        webPageBuilder.addToBody("" + "   " +
                "<input type = \"submit\" value = \"Submit\" />\n" +
                "</form>");
    }

    private void initPageFormStart(HttpServletRequest request, WebPageBuilder webPageBuilder) {
        webPageBuilder.addToBody("" +
                "<form action = \"" +
                new URIBuilder().setPath(title)
                        .addParameter(USER_NAME, request.getParameter(USER_NAME))
                        .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES))
                        .toString()
                + "\" method = \"POST\">\n");
    }

    private void initPageMenuItems(WebPageBuilder webPageBuilder, List<? extends MenuItem> allMenuItems) {
        for (MenuItem menuItem : allMenuItems) {
            webPageBuilder.addToBody("" +
                    "<input type=\"radio\" id=\"" + menuItem.getIndex() + "\"\n" +
                    "     name=\"" + SORTING_MENU_ITEM_INDEX + "\" " +
                    "     value=\"" + menuItem.getIndex() + "\">\n" +
                    "    <label for=\"" + menuItem.getIndex() + "\">" + menuItem.getStr() + "</label>\n" +
                    "<br>\n"
            );
        }
    }
}
