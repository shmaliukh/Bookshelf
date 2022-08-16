package org.vshmaliukh.tomcat_web_app.servlets;

import com.google.gson.Gson;
import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.WebUI;
import org.vshmaliukh.tomcat_web_app.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.*;

public class AddItemServlet extends HttpServlet {

    static Gson gson = new Gson();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        Map<String, String> itemFieldValueMap = WebUtils.readMapOfItemFields(request);
        if (handlerByName.isValidHTMLFormData(itemFieldValueMap)) {
            SaveReadShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);
            Item item = handlerByName.generateItemByParameterValueMap(itemFieldValueMap);
            if (item != null) {
                webShelfHandler.addLiteratureObject(item);
                //webShelfHandler.saveShelfItems();
            }

            WebUtils.redirectTo(ADD_MENU_TITLE, response,
                    WebUtils.generateBaseURLBuilder(ADD_MENU_TITLE, userAtr)
                            .addParameter(ITEM_GSON_STR, gson.toJson(item))
                            .addParameter(ITEM_CLASS_TYPE, itemClassType)
            );
        } else {
            WebUtils.redirectTo(ADD_MENU_TITLE, response, userAtr);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(ADD_ITEM_TITLE);
        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);
        String isRandom = request.getParameter(IS_RANDOM);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        if (itemClassType != null && !itemClassType.equals("")) {
            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

            webPageBuilder.addToBody("" +
                    "<form action = \"" +
                    WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, userAtr)
                            .addParameter(ITEM_CLASS_TYPE, itemClassType) + "\" " +
                    "method = \"POST\">\n" +
                    "Create " + itemClassType + "\n" +
                    "       <br>\n");
            if (isRandom != null) {
                webPageBuilder.addToBody(handlerByName.generateHTMLFormBodyToCreateItem(WebUtils.RANDOM));
            } else {
                webPageBuilder.addToBody(handlerByName.generateHTMLFormBodyToCreateItem());
            }
        }
        webPageBuilder.addButton(WebUtils.generateBaseURLString(ADD_MENU_TITLE, userAtr), ADD_MENU_TITLE);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ADD_ITEM_TITLE, ioe);
        }
    }
}
