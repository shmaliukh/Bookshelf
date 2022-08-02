package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.menus.MainMenu;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.MESSAGE_ENTER_USER_NAME;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class MainMenuServlet extends HttpServlet {
    String title = MAIN_MENU_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInputName = request.getParameter(USER_NAME);
        String userInputNumberStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);

        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        List<MainMenu> allMenuItems = MainMenu.getAllMenuItems();
        webPageBuilder.addToBody("" +
                "<form action = \"" +
                new URIBuilder().setPath(MAIN_MENU_TITLE)
                        .addParameter(USER_NAME, request.getParameter(USER_NAME))
                        .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES))
                        .toString()
                + "\" method = \"POST\">\n");

        for (int i = 0; i < allMenuItems.size() - 1; i++) {
            MainMenu menuItem = allMenuItems.get(i);
            webPageBuilder.addToBody("" +
                            "<input type=\"radio\" id=\"" + menuItem.getMenuItem().getIndex() + "\"\n" +
                            "     name=\"contact\" value=\"email\">\n" +
                            "    <label for=\"" + menuItem.getMenuItem().getIndex() + "\">" + menuItem.getMenuItem().getStr() + "</label>" +
                            "<br>\n"
                    //"<a href=\"" +
                    //        new URIBuilder().setPath(MAIN_MENU_TITLE + "/" + menuItem.getMenuItem().getIndex())
                    //                .addParameter(USER_NAME, userName)
                    //                .addParameter(TYPE_OF_WORK_WITH_FILES, userTypeOfWorkWithFile)
                    //                .toString() + "/\">"
                    //        + menuItem.getMenuItem().getStr()
                    //        + "</a>" + " <br>  <br>\n "
            );
        }
        webPageBuilder.addToBody("" + "   " +
                "<input type = \"submit\" value = \"Submit\" />\n" +
                "</form>");


        webPageBuilder.addButton(LOG_IN_TITLE, "Log out");
        writer.println(webPageBuilder.buildPage());
    }
}
