package org.vshmaliukh.web;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.vshmaliukh.web.menu_servlets.*;

import javax.servlet.Servlet;

public class SimpleWebApp {

    public static final String MY_LINE_SEPARATOR = "<br>";

    public static final String INFORM_MESSAGE = "inform_message";

    public static final String LOG_IN_TITLE = "/";
    public static final String MAIN_MENU_TITLE = "main_menu";
    public static final String ADD_MENU_TITLE = "add_menu";
    public static final String SORTING_TYPES_MENU_TITLE = "sorting_types_menu";
    public static final String ADD_ITEM_TITLE = "add_item";
    public static final String ARRIVE_ITEM_TITLE = "arrive_item_index";
    public static final String BORROW_ITEM_TITLE = "borrow_item_index";
    public static final String DELETE_ITEM_TITLE = "delete_item_index";
    public static final String PRINT_SHELF_TITLE = "print_shelf";

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);


        addServletToTomcat(new LogInServlet(), LOG_IN_TITLE + "*", tomcat, contextPath, context);
        addServletToTomcat(new MainMenuServlet(), MAIN_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new PrintShelfServlet(), PRINT_SHELF_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddMenuServlet(), ADD_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddItemServlet(), ADD_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new ArriveItemServlet(), ARRIVE_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new BorrowItemServlet(), BORROW_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new DeleteItemServlet(), DELETE_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new SortingTypesMenuServlet(), SORTING_TYPES_MENU_TITLE, tomcat, contextPath, context);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static void addServletToTomcat(Servlet servlet, String servletTitle, Tomcat tomcat, String contextPath, Context context) {
        tomcat.addServlet(contextPath, servletTitle + "_servlet", servlet);
        context.addServletMappingDecoded('/' + servletTitle, servletTitle + "_servlet");
    }
}
