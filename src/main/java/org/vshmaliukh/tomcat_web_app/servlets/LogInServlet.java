package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.console_terminal_app.input_handler.ConstantsForConsoleUserInputHandler.*;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.isValidInputInteger;
import static org.vshmaliukh.services.input_services.AbstractInputHandler.isValidInputString;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.WebUtils.INFORM_MESSAGE;

public class LogInServlet extends HttpServlet {

    public static final String USER_NAME = "user_name";
    public static final String TYPE_OF_WORK_WITH_FILES = "type_of_work_with_files";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userInputName = request.getParameter(USER_NAME);
        String userInputNumberStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);
        //Paths.get("")
        //String servletPath = "/${user}/${type_of_work}"

        if (isValidInputString(userInputName, ConstantsForItemInputValidation.PATTERN_FOR_USER_NAME) && isValidInputInteger(userInputNumberStr, ConstantsForItemInputValidation.PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {
            WebUtils.redirectTo(MAIN_MENU_TITLE, response, userAtr);
        } else {
            WebUtils.redirectTo(LOG_IN_TITLE, response,
                    WebUtils.generateBaseURLBuilder(LOG_IN_TITLE, WebUtils.readUserAtr(request))
                            .addParameter(INFORM_MESSAGE, "Wrong input"));
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(LOG_IN_TITLE);

        String userNameStr = request.getParameter(USER_NAME) != null ? request.getParameter(USER_NAME) : "";
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES) != null ? request.getParameter(TYPE_OF_WORK_WITH_FILES) : "";

        webPageBuilder.addToBody(logInFormStr(userNameStr, typeOfWorkWithFilesStr));

        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(LOG_IN_TITLE, ioe);
        }
    }

    private String logInFormStr(String userNameStr, String typeOfWorkWithFilesStr) {
        return "" +
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
                "</form>";
    }
}
