package org.vshmaliukh.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;
import static org.vshmaliukh.terminal.services.input_services.InputHandler.isValidInputInteger;
import static org.vshmaliukh.terminal.services.input_services.InputHandler.isValidInputString;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class LogInServlet extends HttpServlet {

    public static final String USER_NAME = "user_name";
    public static final String TYPE_OF_WORK_WITH_FILES = "type_of_work_with_files";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userInputName = request.getParameter(USER_NAME);
        String userInputNumberStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);

        if (isValidInputString(userInputName, PATTERN_FOR_USER_NAME) && isValidInputInteger(userInputNumberStr, PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {
            WebUtils.redirectTo(MAIN_MENU_TITLE, response, request);
        } else {
            try {
                response.sendRedirect(
                        WebUtils.generateBaseURLBuilder(LOG_IN_TITLE, request)
                                .addParameter(INFORM_MESSAGE, "Wrong input")
                                .toString());
            } catch (IOException ioe) {
                WebUtils.logServletErr(LOG_IN_TITLE, ioe);
            }
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        WebPageBuilder webPageBuilder = new WebPageBuilder(LOG_IN_TITLE);

        String userNameStr = request.getParameter(USER_NAME) != null ? request.getParameter(USER_NAME) : "";
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES) != null ? request.getParameter(TYPE_OF_WORK_WITH_FILES) : "";

        webPageBuilder.addToBody("" +
                "<form action = \"" + LOG_IN_TITLE + "\" method = \"POST\">\n" +
                MESSAGE_ENTER_USER_NAME + "\n" +
                "       <br>\n" +
                "<input type = \"text\" name=\"" + USER_NAME + "\" value=\"" + userNameStr + "\">\n " +
                "       <br>\n" +
                "       <br>\n" +
                MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES + "\n" +
                "       <br>\n" +
                "   <input type = \"number\" name=\"" + TYPE_OF_WORK_WITH_FILES + "\"value=\"" + typeOfWorkWithFilesStr + "\">\n " +
                "       <br>\n" +
                "       <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>");
        WebUtils.addMessageBlock(request, webPageBuilder);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(LOG_IN_TITLE, ioe);
        }
    }
}
