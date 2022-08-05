package org.vshmaliukh.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.vshmaliukh.web.SimpleWebApp.*;

public class MainMenuServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        WebPageBuilder webPageBuilder = new WebPageBuilder(MAIN_MENU_TITLE);

        webPageBuilder.addToBody(initMainMenu(request));
        webPageBuilder.addToBody(WebUtils.generateCurrentStateOfShelf(request));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(MAIN_MENU_TITLE, ioe);
        }
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
