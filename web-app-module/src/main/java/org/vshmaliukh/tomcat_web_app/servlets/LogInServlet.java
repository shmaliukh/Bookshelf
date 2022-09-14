package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.Constants;
import org.vshmaliukh.services.input_handler.ConstantsForConsoleUserInputHandler;
import org.vshmaliukh.services.input_services.AbstractInputHandler;
import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.UrlUtil;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.Constants.INFORM_MESSAGE;

public class LogInServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userInputName = request.getParameter(Constants.USER_NAME);
        String userInputNumberStr = request.getParameter(Constants.TYPE_OF_WORK_WITH_FILES);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        if (AbstractInputHandler.isValidInputString(userInputName, ConstantsForItemInputValidation.PATTERN_FOR_USER_NAME) && AbstractInputHandler.isValidInputInteger(userInputNumberStr, ConstantsForItemInputValidation.PATTERN_FOR_TYPE_OF_WORK_WITH_FILES)) {
            UrlUtil.redirectTo(Constants.MAIN_MENU_TITLE, response, userAtr);
        } else {
            UrlUtil.redirectTo(Constants.LOG_IN_TITLE, response,
                    UrlUtil.generateBaseURLBuilder(Constants.LOG_IN_TITLE, WebUtils.readUserAtr(request))
                            .addParameter(INFORM_MESSAGE, "Wrong input"));
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.LOG_IN_TITLE);

        String userNameStr = request.getParameter(Constants.USER_NAME) != null ? request.getParameter(Constants.USER_NAME) : "";
        String typeOfWorkWithFilesStr = request.getParameter(Constants.TYPE_OF_WORK_WITH_FILES) != null ? request.getParameter(Constants.TYPE_OF_WORK_WITH_FILES) : "";

        webPageBuilder.addToBody(logInFormStr(userNameStr, typeOfWorkWithFilesStr));

        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(Constants.LOG_IN_TITLE, ioe);
        }
    }

    private String logInFormStr(String userNameStr, String typeOfWorkWithFilesStr) {
        return "" +
                "<form action = \"" + Constants.LOG_IN_TITLE + "\" method = \"POST\">\n " +
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_USER_NAME + "\n " +
                "       <br>\n " +
                "<input type = \"text\" name=\"" + Constants.USER_NAME + "\" value=\"" + userNameStr + "\">\n " +
                "       <br>\n " +
                "       <br>\n " +
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES.replaceAll(System.lineSeparator(), System.lineSeparator()+"<br>") + "\n " +
                "       <br>\n " +
                "   <input type = \"number\" name=\"" + Constants.TYPE_OF_WORK_WITH_FILES + "\"value=\"" + typeOfWorkWithFilesStr + "\">\n " +
                "       <br>\n " +
                "       <br>\n " +
                "   <input type = \"submit\" value = \"Submit\" />\n " +
                "</form>";
    }
}
