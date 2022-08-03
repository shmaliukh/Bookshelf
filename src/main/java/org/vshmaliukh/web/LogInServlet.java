package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class LogInServlet extends HttpServlet {

    public static final String USER_NAME = "user_name";
    public static final String TYPE_OF_WORK_WITH_FILES = "type_of_work_with_files";

    String title = LOG_IN_TITLE;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInputName = request.getParameter(USER_NAME);
        String userInputNumberStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);

        InputHandlerForUser inputHandlerForUser = new InputHandlerForUser(null, null);
        if (inputHandlerForUser.isValidInputString(userInputName, PATTERN_FOR_USER_NAME)
                && inputHandlerForUser.isValidInputInteger(userInputNumberStr, PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {

            String redirectorURL = new URIBuilder()
                    .setPath(MAIN_MENU_TITLE)
                    .addParameter(USER_NAME, userInputName)
                    .addParameter(TYPE_OF_WORK_WITH_FILES, userInputNumberStr)
                    .toString();
            response.sendRedirect(redirectorURL);
        } else {
            doGet(request, response); // TODO add message about wrong input
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        webPageBuilder.addToBody("" +
                "<form action = \"" + title + "\" method = \"POST\">\n" +
                MESSAGE_ENTER_USER_NAME + "\n" +
                "       <br>\n" +
                "<input type = \"text\" name=\"" + USER_NAME + "\">\n " +
                "       <br>\n" +
                "       <br>\n" +
                MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES.replaceAll(System.lineSeparator(), " <br> ") + "\n" +
                "       <br>\n" +
                "   <input type = \"number\" name=\"" + TYPE_OF_WORK_WITH_FILES + "\">\n " +
                "       <br>\n" +
                "       <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>");
        writer.println(webPageBuilder.buildPage());
    }
}
