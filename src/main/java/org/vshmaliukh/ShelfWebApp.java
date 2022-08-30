package org.vshmaliukh;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.vshmaliukh.tomcat_web_app.servlets.*;

import javax.servlet.Servlet;

import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

public class ShelfWebApp {

    public static final List<String> USER_PARAMETER_LIST = Collections.unmodifiableList(Arrays.asList(USER_NAME, TYPE_OF_WORK_WITH_FILES));

    public static final String SERVLET_NAME_POSTFIX = "_servlet";

    public static final String LOG_IN_TITLE = "log_in";
    public static final String MAIN_MENU_TITLE = "main_menu";
    public static final String ADD_MENU_TITLE = "add_menu";
    public static final String SORTING_TYPES_MENU_TITLE = "sorting_types_menu";
    public static final String ITEMS_SORTING_MENU_TITLE = "items_sorting_menu";
    public static final String ADD_ITEM_TITLE = "add_item";
    public static final String DELETE_ITEM_TITLE = "delete_item";
    public static final String EDIT_ITEMS_TITLE = "edit_items";
    public static final String CHANGE_ITEM_BORROWED_STATE = "change_item_borrowed_state";

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
//        tomcat.setHost();
        tomcat.setPort(8080);
//        tomcat.getServer().setAddress("127.1.1.1");

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        addServletToTomcat(new LogInServlet(), LOG_IN_TITLE, "/*", tomcat, contextPath, context);
        addServletToTomcat(new MainMenuServlet(), MAIN_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddMenuServlet(), ADD_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddItemServlet(), ADD_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new SortingTypesMenuServlet(), SORTING_TYPES_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new ItemsSortingMenuServlet(), ITEMS_SORTING_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new EditItemsServlet(), EDIT_ITEMS_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new DeleteItemServlet(), DELETE_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new ChangeBorrowedStateItemServlet(), CHANGE_ITEM_BORROWED_STATE, tomcat, contextPath, context);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static void addServletToTomcat(Servlet servlet, String servletTitle, Tomcat tomcat, String contextPath, Context context) {
        tomcat.addServlet(contextPath, servletTitle + SERVLET_NAME_POSTFIX, servlet);
        context.addServletMappingDecoded('/' + servletTitle, servletTitle + SERVLET_NAME_POSTFIX);
    }

    private static void addServletToTomcat(Servlet servlet, String servletTitle, String servletPattern, Tomcat tomcat, String contextPath, Context context) {
        tomcat.addServlet(contextPath, servletTitle + SERVLET_NAME_POSTFIX, servlet);
        context.addServletMappingDecoded(servletPattern, servletTitle + SERVLET_NAME_POSTFIX);
    }
}
