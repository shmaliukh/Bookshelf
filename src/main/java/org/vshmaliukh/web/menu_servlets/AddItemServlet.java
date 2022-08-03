package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.web.WebPageBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class AddItemServlet extends HttpServlet {

    public static final String ITEM_CLASS_TYPE = "item_class_type";

    String title = ADD_ITEM_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);

        ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

    }


        @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String itemClassType = request.getParameter(ITEM_CLASS_TYPE);

        ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);

        webPageBuilder.addToBody("" +
                "<form action = \"" +
                new URIBuilder().setPath(ADD_ITEM_TITLE)
                        .addParameter(USER_NAME, request.getParameter(USER_NAME))
                        .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES))
                        .addParameter(ITEM_CLASS_TYPE, request.getParameter(ITEM_CLASS_TYPE))
                        .toString()
                + "\" method = \"POST\">\n" +
                "Create " + itemClassType + "\n" +
                "       " + MY_LINE_SEPARATOR + "\n" +
                handlerByName.generateHTMLFormBodyToCreateItem());

        writer.println(webPageBuilder.buildPage());
    }
}
