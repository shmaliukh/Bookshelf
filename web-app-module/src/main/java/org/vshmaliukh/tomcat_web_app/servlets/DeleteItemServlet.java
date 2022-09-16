package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.WebUtils;
import org.vshmaliukh.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;
import static org.vshmaliukh.utils.WebUtils.readUserAtr;
import static org.vshmaliukh.Constants.INDEX_OF_ITEM;

public class DeleteItemServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);
        Map<String, String> userAtr = readUserAtr(request);

        try {
            deleteItemByIndex(indexOfItem, userAtr);
        } catch (NumberFormatException nfe) {
            WebUtils.logServletErr(Constants.DELETE_ITEM_TITLE, nfe);
        } finally {
            UrlUtil.redirectTo(Constants.EDIT_ITEMS_TITLE, response, userAtr);
        }
    }

    private void deleteItemByIndex(String indexOfItem, Map<String, String> userAtr) {
        int index = Integer.parseInt(indexOfItem);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        if (webShelfHandler != null) {
            webShelfHandler.deleteItemByIndex(index);
        }
    }
}
