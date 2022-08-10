package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.tomcat_web_app.WebShelfHandler;
import org.vshmaliukh.tomcat_web_app.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.WebUtils.generateShelfHandler;
import static org.vshmaliukh.tomcat_web_app.WebUtils.readUserAtr;
import static org.vshmaliukh.tomcat_web_app.servlets.EditItemsServlet.INDEX_OF_ITEM;

public class DeleteItemServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        Map<String, String> userAtr = readUserAtr(request);

        try {
            int index = Integer.parseInt(indexOfItem);
            WebShelfHandler webShelfHandler = generateShelfHandler(userAtr);
            webShelfHandler.deleteLiteratureObjectByIndex(index);
            webShelfHandler.saveShelfItemsToJson();
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(DELETE_ITEM_TITLE, nfe);
        } finally {
            WebUtils.redirectTo(EDIT_ITEMS_TITLE, response, userAtr);
        }
    }
}
