package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.services.input_handler.ConstantsForConsoleUserInputHandler;
import org.vshmaliukh.services.input_services.AbstractInputHandler;
import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.LOG_IN_TITLE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.MAIN_MENU_TITLE;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.INFORM_MESSAGE;

public class LogInServlet extends HttpServlet {

    public static final String USER_NAME = "userName";
    public static final String TYPE_OF_WORK_WITH_FILES = "typeOfWork";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userInputName = request.getParameter(USER_NAME);
        String userInputNumberStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        if (AbstractInputHandler.isValidInputString(userInputName, ConstantsForItemInputValidation.PATTERN_FOR_USER_NAME) && AbstractInputHandler.isValidInputInteger(userInputNumberStr, ConstantsForItemInputValidation.PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {
            UrlUtil.redirectTo(MAIN_MENU_TITLE, response, userAtr);
        } else {
            UrlUtil.redirectTo(LOG_IN_TITLE, response,
                    UrlUtil.generateBaseURLBuilder(LOG_IN_TITLE, WebUtils.readUserAtr(request))
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
                "<form action = \"" + LOG_IN_TITLE + "\" method = \"POST\">\n " +
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_USER_NAME + "\n " +
                "       <br>\n " +
                "<input type = \"text\" name=\"" + USER_NAME + "\" value=\"" + userNameStr + "\">\n " +
                "       <br>\n " +
                "       <br>\n " +
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES.replaceAll(System.lineSeparator(), System.lineSeparator()+"<br>") + "\n " +
                "       <br>\n " +
                "   <input type = \"number\" name=\"" + TYPE_OF_WORK_WITH_FILES + "\"value=\"" + typeOfWorkWithFilesStr + "\">\n " +
                "       <br>\n " +
                "       <br>\n " +
                "   <input type = \"submit\" value = \"Submit\" />\n " +
                "</form>";
    }
}
