package org.vshmaliukh.tomcat_web_app.servlets;

import com.google.gson.Gson;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.Constants;
import org.vshmaliukh.tomcat_web_app.WebPageBuilder;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.UrlUtil;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.Constants.MENU_ITEM_INDEX;

public class AddMenuServlet extends HttpServlet {

    static Gson gson = new Gson();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        if (menuItemIndex != null && typeOfWorkWithFilesStr != null && !menuItemIndex.equals("") && !typeOfWorkWithFilesStr.equals("")) {
            GeneratedMenu generatedMenu = new GeneratedMenuForAdding();
            int parseInt;
            try {
                parseInt = Integer.decode(menuItemIndex);
            } catch (NumberFormatException e) {
                parseInt = 0;
            }
            if (parseInt > 0 && parseInt <= generatedMenu.generatedMenu.size()) {
                MenuItemClassType<?> menuItemClassType = generatedMenu.getMenuItems().get(parseInt - 1);
                int index = menuItemClassType.getIndex();
                addItemByType(userAtr, response, menuItemClassType, index);
            }
        } else {
            UrlUtil.redirectTo(Constants.ADD_MENU_TITLE, response, userAtr);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebPageBuilder webPageBuilder = new WebPageBuilder(Constants.ADD_MENU_TITLE);
        Map<String, String> userAtr = WebUtils.readUserAtr(request);

        webPageBuilder.addToBody(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");

        webPageBuilder.addToBody(HtmlUtil.generateMenuItemsFormHTML(userAtr, Constants.ADD_MENU_TITLE, new GeneratedMenuForAdding()));
        webPageBuilder.addButton(UrlUtil.generateBaseURLString(Constants.MAIN_MENU_TITLE, userAtr), Constants.MAIN_MENU_TITLE);

        webPageBuilder.addMessageBlock(generateMessageAboutAddedItem(request));

        try {
            response.getWriter().println(webPageBuilder.buildPage());
        } catch (IOException ioe) {
            WebUtils.logServletErr(Constants.ADD_MENU_TITLE, ioe);
        }
    }

    public static String generateMessageAboutAddedItem(HttpServletRequest request) { // todo create test
        String typeOfClass = request.getParameter(Constants.ITEM_CLASS_TYPE);
        String itemStr = request.getParameter(Constants.ITEM_GSON_STR);
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
            UrlUtil.redirectTo(Constants.ADD_ITEM_TITLE, response,
                    UrlUtil.generateBaseURLBuilder(Constants.ADD_ITEM_TITLE, userAtr)
                            .addParameter(Constants.ITEM_CLASS_TYPE, classSimpleName)
                            .addParameter(Constants.IS_RANDOM, "true"));
        } else {
            UrlUtil.redirectTo(Constants.ADD_ITEM_TITLE, response,
                    UrlUtil.generateBaseURLBuilder(Constants.ADD_ITEM_TITLE, userAtr)
                            .addParameter(Constants.ITEM_CLASS_TYPE, classSimpleName));
        }
    }
}
