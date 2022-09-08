package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;

public class MainMenuServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(MAIN_MENU_TITLE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(initMainMenu(userAtr));
        webPageBuilder.addToBody(WebUtils.generateCurrentStateOfShelf(userAtr, ItemTitles.TITLE_LIST));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(MAIN_MENU_TITLE, ioe);
        }
    }

    private String initMainMenu(Map<String, String> userAtr) {
        return "" +
                "MENU:" +
                "   <br>\n " +
                generateMainMenuItem(ADD_MENU_TITLE, "ADD item", userAtr) +
                generateMainMenuItem(EDIT_ITEMS_TITLE, "EDIT items", userAtr) +
                generateMainMenuItem(SORTING_TYPES_MENU_TITLE, "SORT items", userAtr) +
                generateMainMenuItem(LOG_IN_TITLE, "EXIT", userAtr) +
                "   <br>\n " +
                "Current state of shelf:\n " +
                "   <br>\n";
    }

    private String generateMainMenuItem(String servletTitle, String itemTitle, Map<String, String> userAtr) {
        return "<a href=\"" + UrlUtil.generateBaseURLString(servletTitle, userAtr) + "\">" + itemTitle + "</a>\n" +
                "   <br>\n ";
    }
}
