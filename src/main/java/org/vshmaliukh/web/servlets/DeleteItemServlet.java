package org.vshmaliukh.web.servlets;

import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.WebUtils.generateShelfHandler;
import static org.vshmaliukh.web.WebUtils.readUserAtr;
import static org.vshmaliukh.web.servlets.EditItemsServlet.INDEX_OF_ITEM;

public class DeleteItemServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        Map<String, String> userAtr = readUserAtr(request);

        try {
            int index = Integer.parseInt(indexOfItem);
            WebShelfHandler webShelfHandler = generateShelfHandler(userAtr);
            webShelfHandler.getShelf().deleteLiteratureObjectByIndex(index);
            webShelfHandler.saveShelfItemsToJson();
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(DELETE_ITEM_TITLE, nfe);
        } finally {
            WebUtils.redirectTo(EDIT_ITEMS_TITLE, response, userAtr);
        }
    }
}
