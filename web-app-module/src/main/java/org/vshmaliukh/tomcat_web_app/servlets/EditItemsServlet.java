package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.Constants;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.WebUtils;
import org.vshmaliukh.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


import static org.vshmaliukh.utils.WebUtils.readUserAtr;

public class EditItemsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.EDIT_ITEMS_TITLE);
        Map<String, String> userAtr = readUserAtr(request);

        String tableForEditingItems = WebUtils.generateTableForEditingItems(userAtr);
        webPageBuilder.addToBody(tableForEditingItems);

        webPageBuilder.addButton(UrlUtil.generateBaseURLString(Constants.MAIN_MENU_TITLE, userAtr), Constants.MAIN_MENU_TITLE);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(Constants.LOG_IN_TITLE, ioe);
        }
    }
}
