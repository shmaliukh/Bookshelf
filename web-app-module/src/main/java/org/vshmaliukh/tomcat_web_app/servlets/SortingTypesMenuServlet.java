package org.vshmaliukh.tomcat_web_app.servlets;

import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.Constants;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.WebUtils;
import org.vshmaliukh.utils.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.Constants.INFORM_MESSAGE;
import static org.vshmaliukh.Constants.MENU_ITEM_INDEX;
import static org.vshmaliukh.Constants.ITEM_CLASS_TYPE;

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

                UrlUtil.redirectTo(Constants.ITEMS_SORTING_MENU_TITLE, response,
                        UrlUtil.generateBaseURLBuilder(Constants.ITEMS_SORTING_MENU_TITLE, userAtr)
                                .addParameter(ITEM_CLASS_TYPE, classType));
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(Constants.SORTING_TYPES_MENU_TITLE, nfe);
            }
        } else {
            UrlUtil.redirectTo(Constants.SORTING_TYPES_MENU_TITLE, response, userAtr);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.SORTING_TYPES_MENU_TITLE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");
        webPageBuilder.addToBody(HtmlUtil.generateMenuItemsFormHTML(userAtr, Constants.SORTING_TYPES_MENU_TITLE, new GeneratedMenuForSorting()));
        webPageBuilder.addButton(UrlUtil.generateBaseURLString(Constants.MAIN_MENU_TITLE, userAtr), Constants.MAIN_MENU_TITLE);
        webPageBuilder.addMessageBlock(request.getParameter(INFORM_MESSAGE));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(Constants.SORTING_TYPES_MENU_TITLE, ioe);
        }
    }
}
