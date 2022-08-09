package org.vshmaliukh.tomcat_web_app.servlets;

import com.google.gson.Gson;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.tomcat_web_app.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.vshmaliukh.services.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.WebUtils.MENU_ITEM_INDEX;

public class AddMenuServlet extends HttpServlet {

    public static final String ITEM_CLASS_TYPE = "item_class_type";
    public static final String ITEM_GSON_STR = "item_gson_str";
    public static final String IS_RANDOM = "is_random";
    static Gson gson = new Gson();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

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
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");


        webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(userAtr, ADD_MENU_TITLE, new GeneratedMenuForAdding()));
        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, userAtr), MAIN_MENU_TITLE);

        webPageBuilder.addMessageBlock(generateMessageAboutAddedItem(request));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(ADD_MENU_TITLE, ioe);
        }
    }

    private String generateMessageAboutAddedItem(HttpServletRequest request) { // todo create test
        String typeOfClass = request.getParameter(ITEM_CLASS_TYPE);
        String itemStr = request.getParameter(ITEM_GSON_STR);
        if (itemStr != null && typeOfClass != null) {
            Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(typeOfClass);
            Item item = gson.fromJson(itemStr, classByName);

            return "Added new item:" +
                    " <br>\n " +
                    " <br>\n " +
                    WebUtils.generateTableOfShelfItems(Collections.singletonList(item), false);
        }
        return "";
    }


    private void addItemByType(Map<String, String> userAtr, HttpServletResponse response, MenuItemClassType<?> menuItemClassType, int index) {
        String classSimpleName = menuItemClassType.getClassType().getSimpleName();
        if (index % 2 == 0) { //add random item
            WebUtils.redirectTo(ADD_ITEM_TITLE, response,
                    WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, userAtr)
                            .addParameter(ITEM_CLASS_TYPE, classSimpleName)
                            .addParameter(IS_RANDOM, "true"));

        } else {
            WebUtils.redirectTo(ADD_ITEM_TITLE, response,
                    WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, userAtr)
                            .addParameter(ITEM_CLASS_TYPE, classSimpleName));
        }
    }
}
