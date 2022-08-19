package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.services.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.INFORM_MESSAGE;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.MENU_ITEM_INDEX;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;

public class SortingTypesMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        if (menuItemIndex != null && !menuItemIndex.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForSorting();
            try {
                int parseInt = Integer.parseInt(menuItemIndex);
                String classType = generatedMenu.getMenuItems().get(parseInt - 1).getClassType().getSimpleName();

                UrlUtil.redirectTo(ITEMS_SORTING_MENU_TITLE, response,
                        UrlUtil.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, userAtr)
                                .addParameter(ITEM_CLASS_TYPE, classType));
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(SORTING_TYPES_MENU_TITLE, nfe);
            }
        } else {
            UrlUtil.redirectTo(SORTING_TYPES_MENU_TITLE, response, userAtr);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(SORTING_TYPES_MENU_TITLE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
        webPageBuilder.addToBody(HtmlUtil.generateMenuItemsFormHTML(userAtr, SORTING_TYPES_MENU_TITLE, new GeneratedMenuForSorting()));
        webPageBuilder.addButton(UrlUtil.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);
        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(SORTING_TYPES_MENU_TITLE, ioe);
        }
    }
}
