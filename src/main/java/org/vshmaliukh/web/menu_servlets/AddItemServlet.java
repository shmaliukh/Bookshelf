package org.vshmaliukh.web.menu_servlets;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class AddItemServlet extends HttpServlet {

    public static final String ITEM_CLASS_TYPE = "item_class_type";

    final String servletTitle = ADD_ITEM_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);

        ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        if (handlerByName.isValidHTMLFormData(request) && typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")) {
            WebShelfHandler webShelfHandler = new WebShelfHandler(userName, Integer.parseInt(typeOfWorkWithFilesStr));
            Item item = handlerByName.generateItemByHTMLFormData(request, response.getWriter()); // TODO fix writer

            webShelfHandler.getShelf().addLiteratureObject(item);
            webShelfHandler.saveShelfItemsToJson();

            response.sendRedirect(WebUtils.generateBaseURLBuilder(ADD_MENU_TITLE, request)
                            .addParameter(INFORM_MESSAGE, "Added " + item)
                            .toString());
        } else {
            WebUtils.redirectTo(ADD_MENU_TITLE, response, request);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(servletTitle);

        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);
        if (itemClassType != null && !itemClassType.equals("")) {
            ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

            webPageBuilder.addToBody("" +
                    "<form action = \"" +
                    WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, request)
                            .addParameter(ITEM_CLASS_TYPE, itemClassType)
                            .toString()
                    + "\" method = \"POST\">\n" +
                    "Create " + itemClassType + "\n" +
                    "       <br>\n" +
                    handlerByName.generateHTMLFormBodyToCreateItem(request));
        }
        response.getWriter().println(webPageBuilder.buildPage());
    }
}
