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
                "   <br>\n " +
                "<a href=\"" + WebUtils.generateBaseURLString(ADD_MENU_TITLE, request) + "\">" + "ADD item </a>" +
                "   <br>\n " +
                "<a href=\"" + WebUtils.generateBaseURLString(EDIT_ITEMS_TITLE, request) + "\">" + "EDIT items </a>" +
                "   <br>\n " +
                "<a href=\"" + WebUtils.generateBaseURLString(SORTING_TYPES_MENU_TITLE, request) + "\">" + "SORTING items </a>" +
                "   <br>\n " +
                "<a href=\"" + LOG_IN_TITLE + "\">" + "EXIT </a>" +
                "   <br>\n " +
                "   <br>\n " +
                "Current state of shelf:\n " +
                "   <br>\n";
    }
}
