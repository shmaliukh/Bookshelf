package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;

public class MainMenuServlet extends HttpServlet {

    String servletTitle = MAIN_MENU_TITLE;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        WebPageBuilder webPageBuilder = new WebPageBuilder(servletTitle);

        webPageBuilder.addToBody(initMainMenu(request));
        webPageBuilder.addToBody(generateCurrentStateOfShelf(request));

        writer.println(webPageBuilder.buildPage());
    }

    private String generateCurrentStateOfShelf(HttpServletRequest request) {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        if(typeOfWorkWithFilesStr != null && !typeOfWorkWithFilesStr.equals("")){
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            WebTerminal webTerminal = new WebTerminal(userName, typeOfWorkWithFiles);
            return webTerminal.generateTableOfShelf();
        }
        return "";
    }

    private String initMainMenu(HttpServletRequest request) {
        return "" +
                "MENU:" +
                "   <br>\n " +
                "   <br>\n " +
                "<a href=\"" + WebUtils.generateURLString(ADD_MENU_TITLE, request) + "\">" + "ADD item </a>" +
                "   <br>\n " +
                "<a href=\"" + LOG_IN_TITLE + "\">" + "EXIT </a>" +
                "   <br>\n " +
                "   <br>\n ";
    }


}
