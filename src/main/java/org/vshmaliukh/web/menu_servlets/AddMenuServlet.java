package org.vshmaliukh.web.menu_servlets;

import org.vshmaliukh.console_terminal.menus.GeneratedMenu;
import org.vshmaliukh.console_terminal.menus.GeneratedMenuForAdding;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.vshmaliukh.console_terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.BookShelfWebApp.*;
import static org.vshmaliukh.web.WebUtils.MENU_ITEM_INDEX;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class AddMenuServlet extends HttpServlet {

    public static final String IS_RANDOM = "is_random";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);
        List<String> userAtr = WebUtils.readUserAtr(request);
        if (menuItemIndex != null && typeOfWorkWithFilesStr != null && !menuItemIndex.equals("") && !typeOfWorkWithFilesStr.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForAdding();
            int parseInt = 0; // TODO
            try {
                parseInt = Integer.parseInt(menuItemIndex);
            } catch (NumberFormatException nfe) {
                WebUtils.logServletErr(ADD_ITEM_TITLE, nfe);
            }
            MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(parseInt - 1);
            int index = menuItemClassType.getIndex();
            addItemByType(userAtr, response, menuItemClassType, index);
        } else {
            WebUtils.redirectTo(ADD_MENU_TITLE, response, userAtr);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(ADD_MENU_TITLE);
        List<String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
        webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(userAtr, ADD_MENU_TITLE, new GeneratedMenuForAdding()));
        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);
        WebUtils.addMessageBlock(request, webPageBuilder);

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ADD_MENU_TITLE, ioe);
        }
    }


    private void addItemByType(List<String> userAtr, HttpServletResponse response, MenuItemClassType<?> menuItemClassType, int index) {
        try {
            String classSimpleName = menuItemClassType.getClassType().getSimpleName();
            if (index % 2 == 0) { //add random item
                response.sendRedirect(WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, userAtr)
                        .addParameter(ITEM_CLASS_TYPE, classSimpleName)
                        .addParameter(IS_RANDOM, "true")
                        .toString());

            } else {
                response.sendRedirect(WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, userAtr)
                        .addParameter(ITEM_CLASS_TYPE, classSimpleName)
                        .toString());
            }
        } catch (IOException ioe) {
            WebUtils.logServletErr(ADD_MENU_TITLE, ioe);
        }
    }
}
