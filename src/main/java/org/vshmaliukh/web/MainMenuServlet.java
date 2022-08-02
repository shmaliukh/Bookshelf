package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.menus.MainMenu;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.PATTERN_FOR_TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.PATTERN_FOR_USER_NAME;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class MainMenuServlet extends HttpServlet {

    public static final String MENU_ITEM_INDEX = "menu_item_index";
    String title = MAIN_MENU_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInputName = request.getParameter(USER_NAME);
        String userInputNumberStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);
        System.out.println(menuItemIndex);

        //InputHandlerForUser inputHandlerForUser = new InputHandlerForUser(null, null);
        //if (inputHandlerForUser.isValidInputString(userInputName, PATTERN_FOR_USER_NAME)
        //        && inputHandlerForUser.isValidInputInteger(userInputNumberStr, PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {
//
        //    String redirectorURL = new URIBuilder()
        //            .setPath(MAIN_MENU_TITLE)
        //            .addParameter(USER_NAME, userInputName)
        //            .addParameter(TYPE_OF_WORK_WITH_FILES, userInputNumberStr)
        //            .toString();
        //    response.sendRedirect(redirectorURL);
        //} else {
        //    doGet(request, response); // TODO add message about wrong input
        //}

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
                new URIBuilder().setPath(title)
                        .addParameter(USER_NAME, request.getParameter(USER_NAME))
                        .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES))
                        .toString()
                + "\" method = \"POST\">\n");

        for (int i = 0; i < allMenuItems.size() - 1; i++) {
            MainMenu menuItem = allMenuItems.get(i);
            webPageBuilder.addToBody("" +
                            "<input type=\"radio\" id=\"" + menuItem.getMenuItem().getIndex() + "\"\n" +
                            "     name=\"" + MENU_ITEM_INDEX + "\" " +
                            "     value=\"" + MENU_ITEM_INDEX + "\\\">\n" +
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
