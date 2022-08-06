package org.vshmaliukh.web.servlets;

import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.servlets.MainMenuServlet.TITLE_LIST;
import static org.vshmaliukh.web.WebUtils.readUserAtr;

public class EditItemsServlet extends HttpServlet {

    public static final String INDEX_OF_ITEM = "index_of_item";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        WebPageBuilder webPageBuilder = new WebPageBuilder(EDIT_ITEMS_TITLE);
        Map<String, String> userAtr = readUserAtr(request);

        WebShelfHandler webShelfHandler = WebUtils.generateShelfHandler(userAtr);

        if (webShelfHandler.getShelf().getAllLiteratureObjects().isEmpty()) {
            webPageBuilder.addToBody("No available literature IN shelf to edit");
        } else {
            String tableForEditingItems = WebUtils.generateTableForEditingItems(userAtr, request, TITLE_LIST);// TODO not use request as parameter
            webPageBuilder.addToBody(tableForEditingItems);
        }

        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);

        writer.println(webPageBuilder.buildPage());
    }
}
