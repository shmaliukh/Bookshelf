package org.vshmaliukh.web;

import org.vshmaliukh.bookshelf.literature_items.ItemTitles;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.vshmaliukh.web.BookShelfWebApp.*;

public class MainMenuServlet extends HttpServlet {

    public static final List<String> TITLE_LIST = Arrays.asList(ItemTitles.TYPE, ItemTitles.NAME, ItemTitles.PAGES, ItemTitles.BORROWED, ItemTitles.AUTHOR, ItemTitles.DATE, ItemTitles.PUBLISHER);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        WebPageBuilder webPageBuilder = new WebPageBuilder(MAIN_MENU_TITLE);
        List<String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(initMainMenu(userAtr));
        webPageBuilder.addToBody(WebUtils.generateCurrentStateOfShelf(userAtr, TITLE_LIST));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(MAIN_MENU_TITLE, ioe);
        }
    }

    private String initMainMenu(List<String> userAtr) {
        return "" +
                "MENU:" +
                "   <br>\n " +
                generateMainMenuItem(ADD_MENU_TITLE, "ADD item", userAtr) +
                generateMainMenuItem(EDIT_ITEMS_TITLE, "EDIT items", userAtr) +
                generateMainMenuItem(SORTING_TYPES_MENU_TITLE, "SORTING items", userAtr) +
                generateMainMenuItem(LOG_IN_TITLE, "EXIT", userAtr) +
                "   <br>\n " +
                "Current state of shelf:\n " +
                "   <br>\n";
    }

    private String generateMainMenuItem(String servletTitle, String itemTitle, List<String> userAtr) {
        return "" +
                "   <br>\n " +
                "<a href=\"" + WebUtils.generateBaseURLString(servletTitle, userAtr) + "\">" + itemTitle + "</a>\n" +
                "   <br>\n ";
    }
}
