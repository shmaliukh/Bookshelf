package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.WebUtils;
import org.vshmaliukh.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;
import static org.vshmaliukh.Constants.INDEX_OF_ITEM;

public class ChangeBorrowedStateItemServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);
        try {
            changeItemBorrowedState(indexOfItem, userAtr);
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(Constants.CHANGE_ITEM_BORROWED_STATE_TITLE, nfe);
        } finally {
            UrlUtil.redirectTo(Constants.EDIT_ITEMS_TITLE, response, userAtr);
        }
    }

    // todo create test
    private void changeItemBorrowedState(String indexOfItem, Map<String, String> userAtr) {
        int index = Integer.parseInt(indexOfItem);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            webShelfHandler.readShelfItems();
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            webShelfHandler.changeBorrowedStateOfItem(allLiteratureObjects, index);
        }
    }
}
