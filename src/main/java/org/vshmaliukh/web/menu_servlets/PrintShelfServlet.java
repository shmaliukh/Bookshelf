package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
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

public class PrintShelfServlet extends HttpServlet {

    String title = PRINT_SHELF_TITLE;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(baos, true);

        Terminal terminal = new Terminal(null, printWriter); // TODO change later
        terminal.setUser(new User(userName));
        terminal.setTypeOfWorkWithFiles(Integer.parseInt(typeOfWorkWithFiles));
        terminal.setUpGsonHandler();

        terminal.readShelfItemsFromJson();
        terminal.printCurrentStateOfShelf();

        webPageBuilder.addToBody(baos.toString().replace(System.lineSeparator(), " <br> "));
        webPageBuilder.addButton(new URIBuilder()
                .setPath(MAIN_MENU_TITLE)
                .addParameter(USER_NAME, userName)
                .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                .toString(),
                "Button to main menu");
        writer.println(webPageBuilder.buildPage());
    }
}
