package org.vshmaliukh.web;

import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.MESSAGE_ENTER_USER_NAME;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.PATTERN_FOR_USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class LogInServlet extends HttpServlet {
    private String title = LOG_IN_TITLE;
    private String userName = "user_name";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInputName = request.getParameter(userName);
        InputHandlerForUser inputHandlerForUser = new InputHandlerForUser(null, null);
        if (inputHandlerForUser.isValidInputString(userInputName, PATTERN_FOR_USER_NAME)) {
            TERMINAL.setUser(new User(userInputName));
            response.sendRedirect(request.getContextPath() + "/book_shelf/" + SELECT_WORK_WITH_FILES_TITLE);
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
                MESSAGE_ENTER_USER_NAME + "\n" +
                "       <br />\n" +
                "   <input type = \"text\" name = \"" + userName + "\">\n" +
                "       <br />\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "       <br />\n" +
                //"   <input type = \"button\" value = \"Back\" />\n" +
                "</form>");
        writer.println(webPageBuilder.buildPage());
    }
}
