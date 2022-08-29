package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


import static org.vshmaliukh.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.readUserAtr;

public class EditItemsServlet extends HttpServlet {

    public static final String INDEX_OF_ITEM = "index_of_item";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(EDIT_ITEMS_TITLE);
        Map<String, String> userAtr = readUserAtr(request);

        String tableForEditingItems = WebUtils.generateTableForEditingItems(userAtr);
        webPageBuilder.addToBody(tableForEditingItems);

        webPageBuilder.addButton(UrlUtil.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(LOG_IN_TITLE, ioe);
        }
    }
}
