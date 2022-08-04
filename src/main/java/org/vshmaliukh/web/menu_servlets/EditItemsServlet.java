package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class EditItemsServlet extends HttpServlet {

    public static final String INDEX_OF_ITEM = "index_of_item";

    String title = EDIT_ITEMS_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);

        //if (!indexOfItem.equals("")) {
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //ConsoleTerminal consoleTerminal = getTerminal(userName, typeOfWorkWithFiles, baos);
            //consoleTerminal.getShelf().borrowLiteratureObjectFromShelfByIndex(Integer.parseInt(indexOfItem));

            //consoleTerminal.saveShelfItemsToJson();
        //}
        doGet(request, response);
        //response.sendRedirect(new URIBuilder()
        //        .setPath(title)
        //        .addParameter(USER_NAME, userName)
        //        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
        //        .toString());
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);

        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);

        WebShelfHandler webShelfHandler = new WebShelfHandler(userName, Integer.parseInt(typeOfWorkWithFiles));
        //terminal.

        if (webShelfHandler.getShelf().getLiteratureInShelf().isEmpty()) {
            webPageBuilder.addToBody("No available literature IN shelf to borrow");
        } else {
            webPageBuilder.addToBody("Enter INDEX of Literature object to borrow one:");
            //webPageBuilder.addToBody(webShelfHandler.generateTableOfShelf(true, true));



            webPageBuilder.addToBody("" +
                    "<form action = \"" +
                    WebUtils.generateBaseURLString(title, request) +
                    "\" method = \"POST\">\n" +
                    "       <br>\n" +
                    "<input type = \"text\" name=\"" + INDEX_OF_ITEM + "\">\n " +
                    "       <br>\n" +
                    "   <input type = \"submit\" value = \"Submit\" />\n" +
                    "</form>");
        }
        webPageBuilder.addButton(new URIBuilder()
                        .setPath(MAIN_MENU_TITLE)
                        .addParameter(USER_NAME, userName)
                        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                        .toString(),
                "Button to " + MAIN_MENU_TITLE);
        response.getWriter().println(webPageBuilder.buildPage());
    }
}
