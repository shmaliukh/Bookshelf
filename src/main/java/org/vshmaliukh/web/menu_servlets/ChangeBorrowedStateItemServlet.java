package org.vshmaliukh.web.menu_servlets;

import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.WebUtils.generateShelfHandler;
import static org.vshmaliukh.web.menu_servlets.EditItemsServlet.INDEX_OF_ITEM;

public class ChangeBorrowedStateItemServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        List<String> userAtr = WebUtils.readUserAtr(request);
        try{
            int index = Integer.parseInt(indexOfItem);
            WebShelfHandler webShelfHandler = generateShelfHandler(userAtr);

            Item item = webShelfHandler.getShelf().getAllLiteratureObjects().get(index - 1);
            item.setBorrowed(!item.isBorrowed());

            webShelfHandler.saveShelfItemsToJson();
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(CHANGE_ITEM_BORROWED_STATE, nfe);
        }
        finally {
            WebUtils.redirectTo(EDIT_ITEMS_TITLE, response, userAtr);
        }
    }
}
