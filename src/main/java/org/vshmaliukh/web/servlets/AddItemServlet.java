package org.vshmaliukh.web.servlets;

import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.servlets.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.WebUtils.INFORM_MESSAGE;
import static org.vshmaliukh.web.servlets.AddMenuServlet.IS_RANDOM;

public class AddItemServlet extends HttpServlet {

    public static final String ITEM_CLASS_TYPE = "item_class_type";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException { // TODO cover with try-catch
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        Map<String, String> mapFieldValue = WebUtils.readMapOfItemFields(request);
        if (handlerByName.isValidHTMLFormData(mapFieldValue) && typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            WebShelfHandler webShelfHandler = new WebShelfHandler(userName, typeOfWorkWithFiles);
            Item item = handlerByName.generateItemByHTMLFormData(mapFieldValue, response.getWriter()); // FIXME fix writer


            webShelfHandler.getShelf().addLiteratureObject(item);
            webShelfHandler.saveShelfItemsToJson();

            response.sendRedirect(WebUtils.generateBaseURLBuilder(ADD_MENU_TITLE, userAtr)
                    .addParameter(INFORM_MESSAGE, "Added " + item)
                    .toString());
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
                            .addParameter(ITEM_CLASS_TYPE, itemClassType)
                            .toString()
                    + "\" method = \"POST\">\n" +
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
