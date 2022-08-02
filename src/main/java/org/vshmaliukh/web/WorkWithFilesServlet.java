package org.vshmaliukh.web;

import org.vshmaliukh.terminal.services.input_services.InputHandlerForUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class WorkWithFilesServlet extends HttpServlet {
    String title = SELECT_WORK_WITH_FILES_TITLE;
    private String typeOfWorkWithFiles = "type_of_work_with_files";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInputNumberStr = request.getParameter(typeOfWorkWithFiles);
        InputHandlerForUser inputHandlerForUser = new InputHandlerForUser(null, null);
        if (inputHandlerForUser.isValidInputInteger(userInputNumberStr, PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {
            TERMINAL.setTypeOfWorkWithFiles(Integer.parseInt(userInputNumberStr));
            TERMINAL.setUpGsonHandler();

            response.sendRedirect(request.getContextPath() + "/book_shelf/" + MAIN_MENU_TITLE);
        } else {
            doGet(request, response); // TODO add message about wrong input
        }
        //URIBuilder uriBuilder = new URIBuilder(); // TODO use uriBuilder
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        webPageBuilder.addToBody("" +
                "<form action = \"" + title + "\" method = \"POST\">\n" +
                MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES.replaceAll(System.lineSeparator(), " <br /> ") + "\n" +
                "       <br />\n" +
                "   <input type = \"number\" name = \"" + typeOfWorkWithFiles + "\">\n" +
                "       <br />\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>" +
                "   <button onclick=\"window.location.href='" + LOG_IN_TITLE + "';\">Back</button>\n");
        writer.println(webPageBuilder.buildPage());
    }
}
