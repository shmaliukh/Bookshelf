package org.vshmaliukh.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.web.SimpleWebApp.*;

public class MainMenuServlet extends HttpServlet {

    String servletTitle = MAIN_MENU_TITLE;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        WebPageBuilder webPageBuilder = new WebPageBuilder(servletTitle);

        webPageBuilder.addToBody(initMainMenu(request));
        webPageBuilder.addToBody(WebUtils.generateCurrentStateOfShelf(request));

        writer.println(webPageBuilder.buildPage());
    }

    private String initMainMenu(HttpServletRequest request) {
        return "" +
                "MENU:" +
                "   <br>\n " +
                generateMainMenuItem(ADD_MENU_TITLE, "ADD item", request) +
                generateMainMenuItem(EDIT_ITEMS_TITLE, "EDIT items", request) +
                generateMainMenuItem(SORTING_TYPES_MENU_TITLE, "SORTING items", request) +
                generateMainMenuItem(LOG_IN_TITLE, "EXIT", request) +
                "   <br>\n " +
                "Current state of shelf:\n " +
                "   <br>\n";
    }

    private String generateMainMenuItem(String servletTitle, String itemTitle, HttpServletRequest request) {
        return "" +
                "   <br>\n " +
                "<a href=\"" + WebUtils.generateBaseURLString(servletTitle, request) + "\">" + itemTitle + "</a>\n" +
                "   <br>\n ";
    }
}
