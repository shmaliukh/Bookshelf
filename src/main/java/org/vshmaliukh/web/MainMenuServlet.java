package org.vshmaliukh.web;

import org.vshmaliukh.terminal.menus.MainMenu;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.vshmaliukh.web.SimpleWebApp.*;

public class MainMenuServlet extends HttpServlet {
    String title = MAIN_MENU_TITLE;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");


        List<MainMenu> allMenuItems = MainMenu.getAllMenuItems();
        for (int i = 0; i < allMenuItems.size() - 1; i++) {
            MainMenu menuItem = allMenuItems.get(i);
            webPageBuilder.addToBody(menuItem.toString() + " <br> ");
        }

        webPageBuilder.addButton(LOG_IN_TITLE, "Log out");
        writer.println(webPageBuilder.buildPage());
    }
}
