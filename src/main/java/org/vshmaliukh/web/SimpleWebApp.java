package org.vshmaliukh.web;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.vshmaliukh.web.menu_servlets.AddItemServlet;
import org.vshmaliukh.web.menu_servlets.PrintShelfServlet;

import javax.servlet.Servlet;

public class SimpleWebApp {

    public static final String LOG_IN_TITLE = "log_in";
    public static final String MAIN_MENU_TITLE = "main_menu";
    public static final String PRINT_SHELF_TITLE = "print_shelf";
    public static final String ADD_ITEM_TITLE = "add_item";

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        addServletToTomcat(new LogInServlet(), LOG_IN_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new MainMenuServlet(), MAIN_MENU_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new PrintShelfServlet(), PRINT_SHELF_TITLE, tomcat, contextPath, context);
        addServletToTomcat(new AddItemServlet(), ADD_ITEM_TITLE, tomcat, contextPath, context);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static void addServletToTomcat(Servlet servlet, String servletTitle, Tomcat tomcat, String contextPath, Context context) {
        tomcat.addServlet(contextPath, servletTitle + "_servlet", servlet);
        context.addServletMappingDecoded('/' + servletTitle, servletTitle + "_servlet");
    }
}