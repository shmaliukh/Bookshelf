package org.vshmaliukh.tomcat_web_app;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.vshmaliukh.Constants;
import org.vshmaliukh.tomcat_web_app.servlets.*;

import javax.servlet.Servlet;
import java.io.File;

public class ShelfWebApp {

    public static final String SERVLET_NAME_POSTFIX = "_servlet";

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        addServletToTomcat(new LogInServlet(), Constants.LOG_IN_TITLE, "/*", tomcat, contextPath, context);
        addServletToTomcat(new MainMenuServlet(), Constants.MAIN_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddMenuServlet(), Constants.ADD_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddItemServlet(), Constants.ADD_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new SortingTypesMenuServlet(), Constants.SORTING_TYPES_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new ItemsSortingMenuServlet(), Constants.ITEMS_SORTING_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new EditItemsServlet(), Constants.EDIT_ITEMS_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new DeleteItemServlet(), Constants.DELETE_ITEM_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new ChangeBorrowedStateItemServlet(), Constants.CHANGE_ITEM_BORROWED_STATE_TITLE, tomcat, contextPath, context);

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
