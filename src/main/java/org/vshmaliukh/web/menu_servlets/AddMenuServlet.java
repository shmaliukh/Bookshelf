package org.vshmaliukh.web.menu_servlets;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.GeneratedMenuForAdding;
import org.vshmaliukh.terminal.menus.menu_items.MenuItem;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.web.WebPageBuilder;
import org.vshmaliukh.web.WebShelfHandler;
import org.vshmaliukh.web.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;
import static org.vshmaliukh.web.WebUtils.MENU_ITEM_INDEX;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class AddMenuServlet extends HttpServlet {


    String servletTitle = ADD_MENU_TITLE;

    Random random = new Random();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFilesStr = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(MENU_ITEM_INDEX);
        if(menuItemIndex != null && typeOfWorkWithFilesStr != null && !menuItemIndex.equals("") && !typeOfWorkWithFilesStr.equals("")){
            GeneratedMenu generatedMenu = new GeneratedMenuForAdding();
            MenuItemClassType menuItemClassType = generatedMenu.getMenuItems().get(Integer.parseInt(menuItemIndex) - 1);
            int index = menuItemClassType.getIndex();
            addItemByType(request, response, userName, typeOfWorkWithFilesStr, menuItemClassType, index);
        }
        else {
            WebUtils.redirectTo(servletTitle, response, request);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(servletTitle);

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
        webPageBuilder.addToBody(WebUtils.generateMenuItemsFormHTML(request, servletTitle, new GeneratedMenuForAdding()));
        webPageBuilder.addButton(WebUtils.generateBaseURLString(MAIN_MENU_TITLE, request), MAIN_MENU_TITLE);
        WebUtils.addMessageBlock(request, webPageBuilder);

        response.getWriter().println(webPageBuilder.buildPage());
    }


    private void addItemByType(HttpServletRequest request, HttpServletResponse response, String userName, String typeOfWorkWithFilesStr, MenuItemClassType menuItemClassType, int index) throws IOException {
        if (index % 2 == 0) { //add random item
            int typeOfWorkWithFiles = Integer.parseInt(typeOfWorkWithFilesStr);
            WebShelfHandler webShelfHandler = new WebShelfHandler(userName, typeOfWorkWithFiles);

            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(menuItemClassType.getClassType());
            Item item = handlerByClass.getRandomItem(random);

            webShelfHandler.getShelf().addLiteratureObject(item);
            webShelfHandler.saveShelfItemsToJson();

            //Paths.get("")
            //String servletPath = "/${user}/${type_of_work}"
            response.sendRedirect(
                    WebUtils.generateBaseURLBuilder(servletTitle, request)
                            .addParameter(INFORM_MESSAGE, "Added " + item)
                            .toString());
        } else {
            String classSimpleName = menuItemClassType.getClassType().getSimpleName();
            response.sendRedirect(WebUtils.generateBaseURLBuilder(ADD_ITEM_TITLE, request)
                    .addParameter(ITEM_CLASS_TYPE, classSimpleName)
                    .toString());
        }
    }
}
