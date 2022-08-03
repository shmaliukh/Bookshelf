package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;
import org.vshmaliukh.terminal.services.print_table_service.TablePrinter;
import org.vshmaliukh.web.WebPageBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class DeleteItemServlet extends HttpServlet {

    public static final String INDEX_OF_ITEM = "index_of_item";

    String title = DELETE_ITEM_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String indexOfItem = request.getParameter(INDEX_OF_ITEM);

        if (!indexOfItem.equals("")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Terminal terminal = getTerminal(userName, typeOfWorkWithFiles, baos);
            terminal.shelf.deleteLiteratureObjectByIndex(Integer.parseInt(indexOfItem));

            terminal.saveShelfItemsToJson();
        }
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
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Terminal terminal = getTerminal(userName, typeOfWorkWithFiles, baos);
        //terminal.

        if (terminal.shelf.getAllLiteratureObjects().isEmpty()) {
            terminal.printWriter.println("No available literature of Shelf to delete");
        } else {
            terminal.printWriter.println("Enter INDEX of Literature object of Shelf to delete one:");
            TablePrinter.printHTMLTable(terminal.printWriter, ConvertorToStringForLiterature.getTable(terminal.shelf.getAllLiteratureObjects()), true);


            webPageBuilder.addToBody("" +
                    "<form action = \"" +
                    new URIBuilder()
                            .setPath(title)
                            .addParameter(USER_NAME, userName)
                            .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                            .toString()
                    + "\" method = \"POST\">\n" +
                    "       <br>\n" +
                    "<input type = \"text\" name=\"" + INDEX_OF_ITEM + "\">\n " +
                    "       <br>\n" +
                    "   <input type = \"submit\" value = \"Submit\" />\n" +
                    "</form>");
        }

        webPageBuilder.addToBody(baos.toString());

        webPageBuilder.addButton(new URIBuilder()
                        .setPath(MAIN_MENU_TITLE)
                        .addParameter(USER_NAME, userName)
                        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                        .toString(),
                "Button to " + MAIN_MENU_TITLE);
        writer.println(webPageBuilder.buildPage());
    }

    private Terminal getTerminal(String userName, String typeOfWorkWithFiles, ByteArrayOutputStream baos) {
        PrintWriter printWriter = new PrintWriter(baos, true);

        Terminal terminal = new Terminal(null, printWriter); // TODO change later
        terminal.setUser(new User(userName));
        terminal.setTypeOfWorkWithFiles(Integer.parseInt(typeOfWorkWithFiles));
        terminal.setUpGsonHandler();

        terminal.readShelfItemsFromJson();
        return terminal;
    }
}
