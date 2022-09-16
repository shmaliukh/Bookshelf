package org.vshmaliukh.tomcat_web_app.servlets;

import com.google.gson.Gson;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.Constants;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.WebUtils;
import org.vshmaliukh.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AddItemServlet extends HttpServlet {

    static Gson gson = new Gson();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String itemClassType = request.getParameter(Constants.ITEM_CLASS_TYPE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        Map<String, String> itemFieldValueMap = WebUtils.readMapOfItemFields(request);
        SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
        if (handlerByName.isValidHTMLFormData(itemFieldValueMap) && webShelfHandler != null) {
            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
            webShelfHandler.addItem(item);

            UrlUtil.redirectTo(Constants.ADD_MENU_TITLE, response,
                    UrlUtil.generateBaseURLBuilder(Constants.ADD_MENU_TITLE, userAtr)
                            .addParameter(Constants.ITEM_GSON_STR, gson.toJson(item))
                            .addParameter(Constants.ITEM_CLASS_TYPE, itemClassType)
            );
        } else {
            UrlUtil.redirectTo(Constants.ADD_MENU_TITLE, response, userAtr);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.ADD_ITEM_TITLE);
        String itemClassType = request.getParameter(Constants.ITEM_CLASS_TYPE);
        String isRandom = request.getParameter(Constants.IS_RANDOM);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        if (itemClassType != null && !itemClassType.equals("")) {
            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

            webPageBuilder.addToBody("" +
                    "<form action = \"" +
                    UrlUtil.generateBaseURLBuilder(Constants.ADD_ITEM_TITLE, userAtr)
                            .addParameter(Constants.ITEM_CLASS_TYPE, itemClassType) + "\" " +
                    "method = \"POST\">\n" +
                    "Create " + itemClassType + "\n" +
                    "       <br>\n");

            if (isRandom != null) {
                webPageBuilder.addToBody(handlerByName.generateHTMLFormBodyToCreateItem(WebUtils.RANDOM));
            } else {
                webPageBuilder.addToBody(handlerByName.generateHTMLFormBodyToCreateItem());
            }
        }
        webPageBuilder.addButton(UrlUtil.generateBaseURLString(Constants.ADD_MENU_TITLE, userAtr), Constants.ADD_MENU_TITLE);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(Constants.ADD_ITEM_TITLE, ioe);
        }
    }
}
