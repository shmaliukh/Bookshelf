package org.vshmaliukh.web.servlets;

import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.WebUtils.readUserAtr;

public class EditItemsServlet extends HttpServlet {

    public static final String INDEX_OF_ITEM = "index_of_item";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(EDIT_ITEMS_TITLE);
        Map<String, String> userAtr = readUserAtr(request);

        String tableForEditingItems = WebUtils.generateTableForEditingItems(userAtr);
        webPageBuilder.addToBody(tableForEditingItems);

        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(LOG_IN_TITLE, ioe);
        }
    }
}
