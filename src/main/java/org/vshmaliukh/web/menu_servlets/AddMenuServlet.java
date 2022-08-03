package org.vshmaliukh.web.menu_servlets;

import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.terminal.menus.GeneratedMenu;
import org.vshmaliukh.terminal.menus.GeneratedMenuForAdding;
import org.vshmaliukh.terminal.menus.MainMenu;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.web.WebPageBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import static org.vshmaliukh.terminal.menus.GeneratedMenu.MESSAGE_TO_ENTER;
import static org.vshmaliukh.terminal.menus.MainMenu.getByIndex;
import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;
import static org.vshmaliukh.web.SimpleWebApp.*;
import static org.vshmaliukh.web.menu_servlets.AddItemServlet.ITEM_CLASS_TYPE;

public class AddMenuServlet extends HttpServlet {

    public static final String ADD_MENU_ITEM_INDEX = "add_menu_item_index";


    String title = ADD_MENU_TITLE;

    Random random = new Random();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String menuItemIndex = request.getParameter(ADD_MENU_ITEM_INDEX);
        if(menuItemIndex != null){
            GeneratedMenu generatedMenu = new GeneratedMenuForAdding();
            MenuItemClassType menuItemClassType = generatedMenu.getMenuItems().get(Integer.parseInt(menuItemIndex) - 1);
            int index = menuItemClassType.getIndex();
            if (index % 2 == 0) { //add random item
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintWriter printWriter = new PrintWriter(baos, true);

                Terminal terminal = new Terminal(null, printWriter); // TODO change later
                terminal.setUser(new User(userName));
                terminal.setTypeOfWorkWithFiles(Integer.parseInt(typeOfWorkWithFiles));
                terminal.setUpGsonHandler();
                terminal.readShelfItemsFromJson();

                ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(menuItemClassType.getClassType());
                Item item = handlerByClass.getRandomItem(random);
                terminal.shelf.addLiteratureObject(item);
                terminal.informAboutAddedLiteratureObject(item); //TODO add message about added Item
                terminal.saveShelfItemsToJson();

                response.sendRedirect(new URIBuilder()
                        .setPath(title)
                        .addParameter(USER_NAME, userName)
                        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                        .addParameter(INFORM_MESSAGE, baos.toString())
                        .toString());
                //doGet(request, response);
            } else {
                String classSimpleName = menuItemClassType.getClassType().getSimpleName();
                response.sendRedirect(new URIBuilder()
                        .setPath(ADD_ITEM_TITLE)
                        .addParameter(USER_NAME, userName)
                        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                        .addParameter(ITEM_CLASS_TYPE, classSimpleName)
                        .toString());
            }
        }
        else {
            response.sendRedirect(new URIBuilder()
                    .setPath(title)
                    .addParameter(USER_NAME, userName)
                    .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                    .toString());
        }


    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        String userName = request.getParameter(USER_NAME);
        String typeOfWorkWithFiles = request.getParameter(TYPE_OF_WORK_WITH_FILES);
        String informMessage = request.getParameter(INFORM_MESSAGE);

        GeneratedMenu generatedMenu = new GeneratedMenuForAdding();

        webPageBuilder.addToBody(MESSAGE_TO_ENTER + " <br>\n");
        initPageFormStart(request, webPageBuilder);
        initPageMenuItems(webPageBuilder, generatedMenu.getMenuItems());
        initPageFormEnd(webPageBuilder);


        webPageBuilder.addButton(new URIBuilder()
                        .setPath(MAIN_MENU_TITLE)
                        .addParameter(USER_NAME, userName)
                        .addParameter(TYPE_OF_WORK_WITH_FILES, typeOfWorkWithFiles)
                        .toString(),
                "Button to " + MAIN_MENU_TITLE);

        if (informMessage != null) {
            webPageBuilder.addToBody("" +
                    " <br>\n" +
                    " <br>\n" +
                    informMessage +
                    " <br>\n");
        }
        writer.println(webPageBuilder.buildPage());
    }

    private void initPageFormEnd(WebPageBuilder webPageBuilder) {
        webPageBuilder.addToBody("" + "   " +
                "<input type = \"submit\" value = \"Submit\" />\n" +
                "</form>");
    }

    private void initPageFormStart(HttpServletRequest request, WebPageBuilder webPageBuilder) {
        webPageBuilder.addToBody("" +
                "<form action = \"" +
                new URIBuilder().setPath(title)
                        .addParameter(USER_NAME, request.getParameter(USER_NAME))
                        .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES))
                        .toString()
                + "\" method = \"POST\">\n");
    }

    private void initPageMenuItems(WebPageBuilder webPageBuilder, List<MenuItemClassType> allMenuItems) {
        for (MenuItemClassType menuItem : allMenuItems) {
            webPageBuilder.addToBody("" +
                    "<input type=\"radio\" id=\"" + menuItem.getIndex() + "\"\n" +
                    "     name=\"" + ADD_MENU_ITEM_INDEX + "\" " +
                    "     value=\"" + menuItem.getIndex() + "\">\n" +
                    "    <label for=\"" + menuItem.getIndex() + "\">" + menuItem.getStr() + "</label>\n" +
                    "<br>\n"
            );
        }
    }
}
