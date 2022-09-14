package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.Constants;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MainMenuServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.MAIN_MENU_TITLE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(HtmlUtil.initMainMenu(userAtr));
        webPageBuilder.addToBody(WebUtils.generateCurrentStateOfShelf(userAtr, ItemTitles.TITLE_LIST));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(Constants.MAIN_MENU_TITLE, ioe);
        }
    }


}
