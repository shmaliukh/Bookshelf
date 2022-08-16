package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.tomcat_web_app.WebUserHandler;
import org.vshmaliukh.tomcat_web_app.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.WebUtils.generateShelfHandler;
import static org.vshmaliukh.tomcat_web_app.servlets.EditItemsServlet.INDEX_OF_ITEM;

public class ChangeBorrowedStateItemServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        try{
            int index = Integer.parseInt(indexOfItem);
            WebUserHandler webShelfHandler = generateShelfHandler(userAtr);
            webShelfHandler.readShelfItems();
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            webShelfHandler.changeBorrowedStateOfItem(allLiteratureObjects, index);

            //webShelfHandler.saveShelfItems();

        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(CHANGE_ITEM_BORROWED_STATE, nfe);
        }
        finally {
            WebUtils.redirectTo(EDIT_ITEMS_TITLE, response, userAtr);
        }
    }
}
